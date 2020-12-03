package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * A default implementation for
 * {@link ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.AccessibilityService}
 * */
@AllArgsConstructor
public class AccessibilityServiceImpl implements AccessibilityService {

    private static final Logger LOGGER = Logger.getLogger(
        AccessibilityServiceImpl.class
    );

    private static final int MILLISECONDS_IN_SECONDS = 1000;

    private final SessionFactory sessionFactory;

    @Override
    public HttpAccessibilityCheck checkAccess(
        URL targetUrl,
        int connectionTimeout,
        User user
    ) {
        HttpAccessibilityCheck httpAccessibilityCheck =
            new HttpAccessibilityCheck(
                null,
                targetUrl,
                user,
                null,
                null,
                HttpAccessibilityCheck.TIMEOUT_UNITS.toString(),
                null,
                null
            );
        Integer responseCode = null;
        Double responseTime = null;
        long timeBeforeConnection;
        try {
            HttpURLConnection httpConnection;
            httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setConnectTimeout(
                connectionTimeout * MILLISECONDS_IN_SECONDS
            );
            timeBeforeConnection = System.currentTimeMillis();
            httpConnection.connect();
            responseTime =
                (double) (System.currentTimeMillis() - timeBeforeConnection);
            responseCode = httpConnection.getResponseCode();
            httpConnection.disconnect();
        } catch (MalformedURLException e) {
            LOGGER.error(e);
            throw new UncheckedIOException(e);
        } catch (SocketTimeoutException e) {
            LOGGER.error(e);
            httpAccessibilityCheck.setDescription(
                "The connection timeout has been reached. "
                    + "The "
                    + targetUrl
                    + " has not responded"
            );
        } catch (IOException e) {
            LOGGER.error(e);
            httpAccessibilityCheck.setDescription(
                "Failed to connect to "
                    + targetUrl
                    + ". Please, contact the administrator"
            );
        } finally {
            httpAccessibilityCheck.setResponseTime(responseTime);
            httpAccessibilityCheck.setResponseCode(responseCode);
            httpAccessibilityCheck.setCheckedWhen(ZonedDateTime.now());
            try (Session session = sessionFactory.openSession()) {
                Transaction t = session.beginTransaction();
                session.save(httpAccessibilityCheck);
                session.flush();
                t.commit();
            }
        }
        return httpAccessibilityCheck;
    }
}
