package playground.main;

import playground.model.Country;
import playground.model.Location;
import playground.model.Region;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

//https://thoughts-on-java.org/jpql/

/**
 * JOINs of unrelated entities are not supported by the JPA specification, but you can use a theta join which creates
 * a cartesian product and restricts it in the WHERE clause to the records with matching foreign and primary keys.
 * <p>
 * https://thoughts-on-java.org/how-to-join-unrelated-entities/
 */
public class MainJPA_Region_JPQL {

    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();

        Query query = entityMgr.createQuery("select R  from Region R");
        List<Region> regionList = query.getResultList();
        for (Region r : regionList) {
            r.getCountries().forEach(x -> {
                System.out.println("Region: " + x.getRegion().getRegionName() + ", Country: " + x.getCountryName());
            });
        }

        Query query2 = entityMgr.createQuery("select R  from Region R JOIN R.countries ");
        List<Region> regionList2 = query2.getResultList();
        for (Region r : regionList2) {
            r.getCountries().forEach(x -> {
                System.out.println("Region: " + x.getRegion().getRegionName() + ", Country: " + x.getCountryName());
            });
        }

        Query query3 = entityMgr.createQuery("select r, c  from Region r JOIN r.countries c ");
        List<Object[]> regionList3 = query3.getResultList();

        regionList3.forEach(x -> {
            System.out.println("Region: " + ((Region) x[0]).getRegionName() + ", Country: " + ((Country) x[1]).getCountryName());
        });

        Query query4 = entityMgr.createQuery("select R.regionName  from Region R ");
        List<String> regionNameList = query4.getResultList();
        for (String r : regionNameList) {
            System.out.println("Regions : " + r);
        }

        //cartesian product un-related tables
        //entityMgr.createQuery("select l, r FROM Location l, Region r");
        //Where condition below to avoid cartesian product, where 23 locations and associated region is fetched
        Query query5 = entityMgr.createQuery("select l, r FROM Location l, Region r WHERE l.country.region = r ");
        List<Object[]> locationRegionList = query5.getResultList();
        locationRegionList.forEach(x -> {
            System.out.println("Location: [State: " + ((Location) x[0]).getState() + ", City: " + ((Location) x[0]).getCity() + "] belongs to " +
                    "Region: " + ((Region) x[1]).getRegionName());
        });

        //LEFT JOIN, get all regions (including that don't have country, example antartica and north pole is added
        // (total 6 regions and 25 countries which belong to 4 regions only )
        //Opposite - "select c, r FROM Country c LEFT JOIN c.region r
        //select r, c FROM Region r LEFT JOIN r.countries r
        // Oracle sql = select r.*, c.* from regions r LEFT JOIN countries c ON c.region_id = r.region_id
        Query query6 = entityMgr.createQuery("select r, c FROM Region r LEFT JOIN r.countries c ");
        List<Object[]> countryRegionList = query6.getResultList();

        countryRegionList.stream().forEach(x -> {
            System.out.println("Region: [Region: " + ((Region) x[0]).getRegionName() + ", Country: " + (((Country) x[1]) != null ? ((Country) x[1]).getCountryName() : null) + "]");
        });
        entityMgr.close();
        entityMgrFactory.close();
    }
}
