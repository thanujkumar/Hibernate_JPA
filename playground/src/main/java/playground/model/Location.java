package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.hibernate.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The persistent class for the LOCATIONS database table.
 * <p>
 * Multiple locations belong to a country
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "LOCATIONS")
@NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
public class Location extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "LOCATIONS_SEQ_GENERATOR", sequenceName = "LOCATIONS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATIONS_SEQ_GENERATOR")
    @Column(name = "LOCATION_ID", unique = true, nullable = false)
    private long locationId;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "POSTAL_CODE", length = 20)
    private String postalCode;

    @Column(length = 50)
    private String city;

    @Column(name = "STATE", length = 50)
    private String state;

    //bi-directional many-to-one association to Country
    //Many locations belongs to one country
    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

}
