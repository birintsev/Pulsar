package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker;

import java.io.Serializable;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a configuration item
 * for scheduling active HTTP/HTTPS accessibility checks
 *
 * @see HttpAccessibilityCheckResult
 * @see ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.services.activetracker.HttpAccessibilityService
 * */
@Entity
@Table(name = "pu_active_http_check_config")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpAccessibilityCheckConfiguration implements Serializable {

    private static final ChronoUnit TRIGGERS_CHRONO_UNITS = ChronoUnit.MILLIS;

    @Id
    @Column(name = "target_url")
    private URL targetUrl;

    @Column
    private Duration interval;

    /**
     * ISO-8601 second-based representation
     * */
    @Column(name = "response_timeout")
    private Duration responseTimeout;
}
