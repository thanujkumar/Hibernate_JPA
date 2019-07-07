package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.hibernate.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the COUNTRIES database table.
 * <p>
 * Multiple countries belong to a region.
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "COUNTRIES")
@NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c")
public class Country extends AuditAndOptimisticField implements Serializable {

    @Id
    @SequenceGenerator(name = "COUNTRIES_SEQ_GENERATOR", sequenceName = "COUNTRIES_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUNTRIES_SEQ_GENERATOR")
    @Column(name = "COUNTRY_ID", unique = true, nullable = false, length = 2)
    private String countryId;

    @Column(name = "COUNTRY_NAME", nullable = false, length = 40)
    private String countryName;

    @Version
    @Column(name = "VERSION", nullable = false, precision = 18)
    private BigDecimal version;

    //bi-directional many-to-one association to Region
    //many countries belong to one  region
    @ManyToOne
    @JoinColumn(name = "REGION_ID")
    private Region region;

    //bi-directional many-to-one association to Location
    //One country has many locations
    @OneToMany(mappedBy = "country")
    private List<Location> locations;

    public Location addLocation(Location location) {
        getLocations().add(location);
        location.setCountry(this);

        return location;
    }

    public Location removeLocation(Location location) {
        getLocations().remove(location);
        location.setCountry(null);

        return location;
    }
}
