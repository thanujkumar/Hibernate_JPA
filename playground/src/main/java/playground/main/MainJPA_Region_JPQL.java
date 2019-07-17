package playground.main;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import lombok.Data;
import org.slf4j.LoggerFactory;
import playground.model.Country;
import playground.model.Location;
import playground.model.Region;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

//https://thoughts-on-java.org/jpql/

/**
 * JOINs of unrelated entities are not supported by the JPA specification, but you can use a theta join which creates
 * a cartesian product and restricts it in the WHERE clause to the records with matching foreign and primary keys.
 * <p>
 * https://thoughts-on-java.org/how-to-join-unrelated-entities/
 */
public class MainJPA_Region_JPQL extends Logging {

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

//        Query query7 = entityMgr.createQuery("SELECT  o.customer.name as CUSTOMER, sum(oi.quantity*oi.unitPrice) as SALES_AMOUNT, EXTRACT(year from o.orderDate) as YEAR  FROM Order o INNER JOIN o.orderItems oi INNER JOIN o.customer c WHERE o.status='Shipped' GROUP BY o.customer.name, EXTRACT(year from o.orderDate)");
//        List<Object[]> salesAmountList = query7.getResultList();
//        salesAmountList.forEach(x -> {
//            System.out.println(x[0] +"--"+x[1]+"---"+x[2]);
//        });

        Query query7 = entityMgr.createQuery("SELECT NEW playground.main.SalesInfo( o.customer.name, sum(oi.quantity*oi.unitPrice), EXTRACT(year from o.orderDate))  FROM Order o INNER JOIN o.orderItems oi INNER JOIN o.customer c WHERE o.status='Shipped' GROUP BY o.customer.name, EXTRACT(year from o.orderDate)");
        List<SalesInfo> salesAmountList = query7.getResultList();
        salesAmountList.forEach(x -> {
            System.out.println(x.getCustomer() + "--" + x.getAmount() + "---" + x.getYear());
        });


        entityMgr.close();
        entityMgrFactory.close();

    }


}
