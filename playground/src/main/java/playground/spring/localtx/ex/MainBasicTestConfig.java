package playground.spring.localtx.ex;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.main.Logging;
import playground.model.Region;
import playground.service.RegionService;
import playground.spring.config.PersistenceJPAJavaConfig;

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

        context.close();//invokes UCP destroy method
    }
}
