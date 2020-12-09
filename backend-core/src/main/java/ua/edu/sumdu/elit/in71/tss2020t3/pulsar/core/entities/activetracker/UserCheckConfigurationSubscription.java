package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.activetracker;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities.User;

@Entity
@Table(name = "pu_user_http_check_subscriptions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCheckConfigurationSubscription implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "tracked_by")
    private User trackedBy;

    @Id
    @ManyToOne
    @JoinColumn(name = "target_url")
    private HttpAccessibilityCheckConfiguration trackedConfiguration;

    @Column(name = "tracked_since")
    private ZonedDateTime trackedSince;
}
