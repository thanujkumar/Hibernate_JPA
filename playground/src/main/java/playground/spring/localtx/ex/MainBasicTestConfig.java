package playground.spring.localtx.ex;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.main.Logging;
import playground.model.Region;
import playground.service.RegionService;
import playground.spring.config.PersistenceJPAJavaConfig;

public class MainBasicTestConfig { // extends Logging {

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
    }
}
