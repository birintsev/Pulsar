package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pu_http_access_check")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpAccessibilityCheck {

    public static final int MAX_CONNECTION_TIMEOUT = 27;

    /**
     * Units in which {@link #MAX_CONNECTION_TIMEOUT} is measured
     * */
    public static final ChronoUnit TIMEOUT_UNITS = ChronoUnit.SECONDS;

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Id
    @Column(name = "request_id")
    private UUID requestId;

    @Column(name = "target_url", nullable = false)
    private URL targetUrl;

    @ManyToOne
    @JoinTable(name = "pu_user_http_checks")
    private User user;

    @Column(name = "checked_when")
    private ZonedDateTime checkedWhen;

    @Column(name = "response_code")
    private Integer responseCode;

    @Column(name = "timeout_cron_units")
    private String timeoutChronoUnits;

    @Column(name = "response_time")
    private Double responseTime;

    @Column
    private String description;
}
