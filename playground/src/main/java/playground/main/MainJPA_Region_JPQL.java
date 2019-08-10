package playground.main;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Tuple;

import playground.model.Country;
import playground.model.Location;
import playground.model.Region;

//https://thoughts-on-java.org/jpql/

/**
 * JOINs of unrelated entities are not supported by the JPA specification, but
 * you can use a theta join which creates a cartesian product and restricts it
 * in the WHERE clause to the records with matching foreign and primary keys.
 * <p>
 * https://thoughts-on-java.org/how-to-join-unrelated-entities/
 * <p>
 * All database statements are executed within the context of a physical transaction, even when we don’t explicitly declare transaction boundaries (BEGIN/COMMIT/ROLLBACK).
 * <p>
 * If you don't declare transaction boundaries explicitly, then each statement will have to be executed in a separate transaction (autocommit mode [commit=true]).
 * This may even lead to opening and closing one connection per statement unless your environment can deal with connection-per-thread binding.
 * <p>
 * Declaring a service as @Transactional will give you one connection for the whole transaction duration, and all statements will use that
 * single isolation connection. This is way better than not using explicit transactions in the first place.
 * <p>
 * On large applications, you may have many concurrent requests, and reducing database connection acquisition request rate will definitely
 * improve your overall application performance.
 * <p>
 * JPA doesn't enforce transactions on read operations. Only writes end up throwing a transaction required exception in case you
 * forget to start a transactional context. Nevertheless, it's always better to declare transaction boundaries even for read-only
 * transactions (in Spring @Transactional allows you to mark read-only transactions, which has a great performance benefit).
 */
public class MainJPA_Region_JPQL extends Logging {

    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();
        //If no transaction then each statement will use one connection (so total 8, if transaction set only 1 connection)
        entityMgr.getTransaction().begin();

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
            System.out.println(
                    "Region: " + ((Region) x[0]).getRegionName() + ", Country: " + ((Country) x[1]).getCountryName());
        });

        Query query4 = entityMgr.createQuery("select R.regionName  from Region R ");
        List<String> regionNameList = query4.getResultList();
        for (String r : regionNameList) {
            System.out.println("Regions : " + r);
        }

        // cartesian product un-related tables
        // entityMgr.createQuery("select l, r FROM Location l, Region r");
        // Where condition below to avoid cartesian product, where 23 locations and
        // associated region is fetched
        Query query5 = entityMgr.createQuery("select l, r FROM Location l, Region r WHERE l.country.region = r ");
        List<Object[]> locationRegionList = query5.getResultList();
        locationRegionList.forEach(x -> {
            System.out.println("Location: [State: " + ((Location) x[0]).getState() + ", City: "
                    + ((Location) x[0]).getCity() + "] belongs to " + "Region: " + ((Region) x[1]).getRegionName());
        });

        // LEFT JOIN, get all regions (including that don't have country, example
        // antartica and north pole is added
        // (total 6 regions and 25 countries which belong to 4 regions only )
        // Opposite - "select c, r FROM Country c LEFT JOIN c.region r
        // select r, c FROM Region r LEFT JOIN r.countries r
        // Oracle sql = select r.*, c.* from regions r LEFT JOIN countries c ON
        // c.region_id = r.region_id
        Query query6 = entityMgr.createQuery("select r, c FROM Region r LEFT JOIN r.countries c ");
        List<Object[]> countryRegionList = query6.getResultList();

        countryRegionList.stream().forEach(x -> {
            System.out.println("Region: [Region: " + ((Region) x[0]).getRegionName() + ", Country: "
                    + (((Country) x[1]) != null ? ((Country) x[1]).getCountryName() : null) + "]");
        });
        // Using Tuples for above query
        query6 = entityMgr.createQuery("select r, c FROM Region r LEFT JOIN r.countries c ", Tuple.class);
        List<Tuple> countryRegionListTuple = query6.getResultList();
        for (Tuple t : countryRegionListTuple) {
            System.out.println(t);
        }

//        Query query7 = entityMgr.createQuery("SELECT  o.customer.name as CUSTOMER, sum(oi.quantity*oi.unitPrice) as SALES_AMOUNT, EXTRACT(year from o.orderDate) as YEAR  FROM Order o INNER JOIN o.orderItems oi INNER JOIN o.customer c WHERE o.status='Shipped' GROUP BY o.customer.name, EXTRACT(year from o.orderDate)");
//        List<Object[]> salesAmountList = query7.getResultList();
//        salesAmountList.forEach(x -> {
//            System.out.println(x[0] +"--"+x[1]+"---"+x[2]);
//        });

        //https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/
        Query query7 = entityMgr.createQuery(
                "SELECT NEW playground.main.SalesInfo( o.customer.name, sum(oi.quantity*oi.unitPrice), EXTRACT(year from o.orderDate))  FROM Order o INNER JOIN o.orderItems oi INNER JOIN o.customer c WHERE o.status='Shipped' GROUP BY o.customer.name, EXTRACT(year from o.orderDate)");
        List<SalesInfo> salesAmountList = query7.getResultList();
        salesAmountList.forEach(x -> {
            System.out.println(x.getCustomer() + "--" + x.getAmount() + "---" + x.getYear());
        });

        entityMgr.getTransaction().commit();
        entityMgr.close();
        entityMgrFactory.close();

    }

}
