package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pu_emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    public static final int MAX_EMAIL_BODY_LENGTH = 4000;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID emailId;

    @OneToOne
    private User user;

    @Column
    private String subject;

    @Column(length = MAX_EMAIL_BODY_LENGTH)
    private String body;

    @Column(name = "sent_when")
    private ZonedDateTime sentWhen;
}
