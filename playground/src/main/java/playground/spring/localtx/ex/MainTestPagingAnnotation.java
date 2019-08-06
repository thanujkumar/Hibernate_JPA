package playground.spring.localtx.ex;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import playground.main.Logging;
import playground.spring.config.PersistenceJPAJavaConfig;
import playground.ws.RegionWS;

public class MainTestPagingAnnotation extends Logging {

    static AbstractApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(PersistenceJPAJavaConfig.class);
        RegionWS regionWS = context.getBean("regionWS", RegionWS.class);
        regionWS.getRegionList(null, new RequestType());

    }
}
