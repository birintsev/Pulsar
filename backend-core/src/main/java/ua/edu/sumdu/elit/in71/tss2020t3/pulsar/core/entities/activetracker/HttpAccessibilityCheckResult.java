package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker;

import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pu_http_access_check")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpAccessibilityCheckResult implements Serializable {

    public static final Duration MAX_CONNECTION_TIMEOUT = Duration.of(
        27,
        ChronoUnit.SECONDS
    );

    @Id
    @ManyToOne
    private HttpAccessibilityCheckConfiguration configuration;

    @Id
    @Column(name = "checked_when")
    private ZonedDateTime checkedWhen;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "response_time")
    private Duration responseTime;

    @Column
    private String description;
}
