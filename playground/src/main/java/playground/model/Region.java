package playground.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import playground.base.AuditAndOptimisticField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Persistence class for REGIONS database table.
 * <p>
 * Regions are like Asia, Europe, America etc, each region has many countries.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "REGIONS")
@NamedQuery(name = Region.QUERY_ALL, query = "SELECT r FROM Region r")
public class Region extends AuditAndOptimisticField implements Serializable {

    public static final String QUERY_ALL="Region.findAll";

    @Id
    @SequenceGenerator(name = "REGIONS_SEQ_GENERATOR", sequenceName = "REGIONS_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//, generator = "REGIONS_SEQ_GENERATOR")
    @Column(name = "REGION_ID", unique = true, nullable = false)
    private long regionId;

    @Column(name = "REGION_NAME", nullable = false, length = 50)
    private String regionName;

    //bi-directional many-to-one association to Country
    //one region has many countries
    @OneToMany(mappedBy = "region")
    private List<Country> countries;//Country table will have region_id

    public Country addCountry(Country country) {
        getCountries().add(country);
        country.setRegion(this);

        return country;
    }

    public Country removeCountry(Country country) {
        getCountries().remove(country);
        country.setRegion(null);

        return country;
    }
}
