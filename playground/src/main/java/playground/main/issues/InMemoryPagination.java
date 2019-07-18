package playground.main.issues;

import org.hibernate.jpa.QueryHints;
import playground.main.Logging;
import playground.model.Country;
import playground.model.Region;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.QueryHint;
import java.util.List;

/**
 * https://vladmihalcea.com/fix-hibernate-hhh000104-entity-fetch-pagination-warning-message/
 * https://vladmihalcea.com/fluent-api-entity-building-with-jpa-and-hibernate/
 * https://vladmihalcea.com/jpa-hibernate-query-hints/
 * <p>
 * If you’ve been using Hibernate long enough, then you surely must have seen this WARN log message when doing
 * pagination while join-fetching multiple entities.
 */
public class InMemoryPagination extends Logging {
    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();

        //To avoid below in-memory pagination two queries are required as below
        List<Long> regionIds = entityMgr.createQuery("select r.regionId from Region r")
                .setMaxResults(2).setFirstResult(3).getResultList();
        for (long rid : regionIds) {
            System.out.println("Region Id: " + rid);
        }
        List<Region> regionList = entityMgr.createQuery("select DISTINCT r from Region r JOIN FETCH r.countries c WHERE r.regionId in (:regionIds)")
                .setParameter("regionIds", regionIds).setHint("hibernate.query.passDistinctThrough", false).getResultList();
        for (Region r : regionList) {
            System.out.println(r.getRegionName());
            List<Country> countryList = r.getCountries();
            for (Country c : countryList) {
                System.out.println("\t" + c.getCountryName());
            }
        }

        //Below is issue which is trying to fetch and do pagination in memory - property hibernate.query.fail_on_pagination_over_collection_fetch is set to true in persistence.xml
        regionList = entityMgr.createQuery("select DISTINCT r from Region r JOIN FETCH r.countries c")
                .setMaxResults(2).setFirstResult(3).getResultList();

        for (Region r : regionList) {
            System.out.println(r.getRegionName());
            List<Country> countryList2 = r.getCountries();
            for (Country c : countryList2) {
                System.out.println("\t" + c.getCountryName());
            }
        }

        entityMgr.close();
        entityMgrFactory.close();
    }

}
