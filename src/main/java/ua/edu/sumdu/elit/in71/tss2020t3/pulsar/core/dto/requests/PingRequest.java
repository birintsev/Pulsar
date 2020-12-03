package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.dto.requests;

import java.net.URL;
import java.util.regex.Pattern;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

import static ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck.MAX_CONNECTION_TIMEOUT;

/**
 * This is a POJO representing a user request to check host availability
 * */
@Data
public class PingRequest {

    @NotNull(message = "The target URL must be set")
    private URL targetHost;

    /**
     * Connection timeout
     *
     * @see ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck#MAX_CONNECTION_TIMEOUT
     * @see ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.HttpAccessibilityCheck#TIMEOUT_UNITS
     * */
    @Min(
        value = 1,
        message = "Timeout can not be less than 1 second"
    )
    @Max(
        value = MAX_CONNECTION_TIMEOUT,
        message = "The max connection timeout should be"
            + " less than or equal to " + MAX_CONNECTION_TIMEOUT + " seconds"
    )
    private Integer connectionTimeout;

    /**
     * Just a default constructor
     * */
    public PingRequest() {
    }

    /**
     * An all-args-constructor
     *
     * @param targetHost        a host to be checked
     * @param connectionTimeout a connection timeout
     *                          after which the host
     *                          is considered as unavailable
     * */
    public PingRequest(URL targetHost, int connectionTimeout) {
        validateHttpProtocol(targetHost);
        this.targetHost = targetHost;
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * A setter for {@link #targetHost} property
     * with a check that passed URL has HTTP or HTTPS protocol
     *
     * @param     targetHost               a {@link URL} to be set.
     *                                     Must be of HTTP or HTTPS protocol.
     * @exception IllegalArgumentException if the {@code targetHost}
     *                                     has different from HTTP
     *                                     or HTTPS protocol
     * */
    public void setTargetHost(URL targetHost) {
        validateHttpProtocol(targetHost);
        this.targetHost = targetHost;
    }

    private void validateHttpProtocol(URL url) {
        Pattern protocolPattern = Pattern.compile(
            "^http(s)?$",
            Pattern.CASE_INSENSITIVE
        );
        String protocol = url.getProtocol();
        if (!protocolPattern.matcher(protocol).matches()) {
            throw new IllegalArgumentException(
                "Only http and https protocols are supported"
            );
        }
    }
}
