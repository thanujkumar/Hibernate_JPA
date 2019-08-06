package playground.spring.localtx.ex;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.main.Logging;
import playground.model.Region;
import playground.service.RegionService;
import playground.spring.config.PersistenceJPAJavaConfig;
import playground.spring.paging.Paging;

import java.util.List;

//https://thoughts-on-java.org/5-common-hibernate-mistakes-that-cause-dozens-of-unexpected-queries/

//TODO - Read below link
//https://www.ibm.com/developerworks/java/library/j-ts2x`/index.html

/**
 * Transaction Models
 * <li>The Local Transaction model
 * <li>The Programmatic Transaction model
 * <li>The Declarative Transaction model
 *
 * <p>
 * Transaction Stratergies
 * <li>Client Orchestration transaction strategy
 * <li>API Layer transaction strategy
 * <li>High Concurrency transaction strategy
 * <li>High-Speed Processing transaction strategy
 */

public class MainBasicTestConfig  extends Logging {

    static AbstractApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(PersistenceJPAJavaConfig.class);
        RegionService regionService = context.getBean("regionService", RegionService.class);
        regionService.findAll();

        Region r = new Region();
        r.setRegionName("Test");
        r.setCreatedBy("e212731");
        r.setVersion(0L);
        regionService.insert(r);

        System.out.println(r.getRegionId());

        //Change name and update
        r.setRegionName("Test2");
        r.setModifiedBy("e212731");
        regionService.update(r);

        //Paging
        Paging paging = new Paging(0,2);
        List<Region> regionList = regionService.findAll(paging);
        System.out.println("Size : "+ regionList.size());

        context.close();//invokes UCP destroy method
    }
}
