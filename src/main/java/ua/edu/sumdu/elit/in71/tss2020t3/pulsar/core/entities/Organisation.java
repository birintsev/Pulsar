package ua.edu.sumdu.elit.in71.tss2020t3.pulsar.core.entities;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * Represents a Hibernate POJO of an organisation entity
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pu_organisations")
public class Organisation implements Serializable {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Id
    private UUID id;

    /**
     * Organisation name
     * */
    @Column(nullable = false)
    private String name;

    /**
     * A creator of an organisation
     * */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User owner;

    /**
     * Organisation members
     * */
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        targetEntity = User.class
    )
    @JoinTable(name = "pu_organisation_members")
    private Set<User> members;

    public ID getId() {
        return new ID();
    }

    /**
     * This class is an abstraction that provides a unified way to get an ID
     * of current organisation
     * (for read-only operations such as find-by-id, exists-by-id and other)
     * */
    public final class ID { // todo create similar class in other db-entities

        private ID() {
        }

        public UUID getOrganisationId() {
            return id;
        }
    }
}
