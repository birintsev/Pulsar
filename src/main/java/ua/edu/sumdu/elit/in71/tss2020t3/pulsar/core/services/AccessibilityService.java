package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services;

import java.net.URL;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

/**
 * This service provides methods for checking hosts for availability.
 * <p>
 * For now HTTP(s) check is supported only.
 * If other protocols support is required, this service
 * is the right place to add corresponding checking methods.
 * */
public interface AccessibilityService {

    /**
     * Checks if a host pointed by the {@code targetUrl} is available.
     * <p>
     * A host is considered available if:
     * <ol>
     *     <li> It has respond in time that is less than {@code connectionTimeout}
     *     <li> The response code is a success code (i.e. any of 2XX responses)
     * </ol>
     * <p>
     * Note, that this method does not throw {@link java.io.IOException}
     * if it happens during establishing a connection.
     * In such cases the host will be considered as unavailable.
     *
     * @param     targetUrl                a url of the host being checked
     * @param     connectionTimeout        a timeout after which the host
     *                                     is considered as unavailable
     *                                     (even if it's sent a 2XX response)
     *                                     This value should be
     *                                     a natural number that is less than
     *                                     or equal to {@link HttpAccessibilityCheck#MAX_CONNECTION_TIMEOUT}
     * @param     user                     a user who has requested
     *                                     the accessibility check
     * @return                             a result
     *                                     of the host availability check
     * @exception IllegalArgumentException if {@code connectionTimeout}
     *                                     is not a natural number
     *                                     that is less than or equal to
     *                                     {@link HttpAccessibilityCheck#MAX_CONNECTION_TIMEOUT}
     * */
    HttpAccessibilityCheck checkAccess(
        URL targetUrl,
        int connectionTimeout,
        User user
    );
}
