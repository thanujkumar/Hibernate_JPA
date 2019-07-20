package playground.main.issues;

import playground.main.Logging;
import playground.model.Country;
import playground.model.Region;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

//https://thoughts-on-java.org/common-hibernate-mistakes-cripple-performance/

/**
 * JPA specification defines the FetchType.LAZY as the default for all to-many associations and default FetchType.EAGER
 * for all to-one associations.
 * <p>
 * JPQL doesn’t support the OFFSET and LIMIT keywords JPQL Query. It should be informed to Query interface and not in the JPQL statement.
 */
public class NPlusOneProblem extends Logging {

    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();
       //No transction used

        List<Region> regionList = entityMgr.createQuery("select r from Region r").getResultList();

        //If country was eager JPA would have loadded in N+1 way and also when lazy accessing country would generate N+1 queries
        for (Region r : regionList) {
            System.out.println(r.getRegionName());
            List<Country> countryList = r.getCountries();
            for (Country c : countryList) {
                System.out.println("\t" + c.getCountryName());
            }
        }


        entityMgr.close();
        entityMgrFactory.close();

        //DISTINCT not given, here we get 25 results, but expect 5 regions and countries to have all
        entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        entityMgr = entityMgrFactory.createEntityManager();

        regionList = entityMgr.createQuery("select r from Region r JOIN FETCH r.countries c").getResultList();

        //N+1 avoided due to above JPQL which initializes required association
        for (Region r : regionList) {
            System.out.println(r.getRegionName());
            List<Country> countryList = r.getCountries();
            for (Country c : countryList) {
                System.out.println("\t" + c.getCountryName());
            }
        }

        entityMgr.close();
        entityMgrFactory.close();


        //You can easily avoid that, when you tell Hibernate to initialize the required association
        entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        entityMgr = entityMgrFactory.createEntityManager();

        regionList = entityMgr.createQuery("select DISTINCT r from Region r JOIN FETCH r.countries c").getResultList();

        //N+1 avoided due to above JPQL which initializes required association
        for (Region r : regionList) {
            System.out.println(r.getRegionName());
            List<Country> countryList = r.getCountries();
            for (Country c : countryList) {
                System.out.println("\t" + c.getCountryName());
            }
        }

        entityMgr.close();
        entityMgrFactory.close();

        //You can easily avoid that, when you tell Hibernate to initialize the required association
        entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        entityMgr = entityMgrFactory.createEntityManager();

        Region region = entityMgr.createQuery("select r from Region r JOIN FETCH r.countries c where r.regionId = 1", Region.class).getSingleResult();

        //N+1 avoided due to above JPQL which initializes required association
        System.out.println(region.getRegionName());
        List<Country> countryList = region.getCountries();
        for (Country c : countryList) {
            System.out.println("\t" + c.getCountryName());
        }

        entityMgr.close();
        entityMgrFactory.close();

        //Limit/ Offset
        entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        entityMgr = entityMgrFactory.createEntityManager();
        List<Country> countryList2 = entityMgr.createQuery("select c FROM Country c")
                .setMaxResults(10).setFirstResult(10).getResultList();

        for (Country c : countryList2) {
            System.out.println(c.getCountryName());
        }

        entityMgr.close();
        entityMgrFactory.close();
    }
}



