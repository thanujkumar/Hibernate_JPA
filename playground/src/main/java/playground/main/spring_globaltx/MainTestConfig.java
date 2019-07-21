package playground.main.spring_globaltx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.main.Logging;
import playground.model.Region;
import playground.service.RegionService;
import playground.spring.globaltx.JavaConfigGlobalTx;

import java.util.List;

public class MainTestConfig extends Logging {
    private static AbstractApplicationContext context;

    public static void main(String[] args) throws Exception{
        context = new AnnotationConfigApplicationContext(JavaConfigGlobalTx.class);

        RegionService regionService = context.getBean("regionService", RegionService.class);
        List<Region> regionList = regionService.findAll();
        for (Region r : regionList) {
            System.out.println(r.getRegionName());
        }

        Region region = new Region();
        region.setRegionName("TestRegion");
        region.setCreatedBy("Thanuj");
        region.setVersion(0L);

        regionService.insert(region);

        context.close();
    }
}
