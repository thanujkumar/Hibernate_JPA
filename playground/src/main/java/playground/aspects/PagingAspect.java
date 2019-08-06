package playground.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import playground.spring.paging.SrvPaging;

@Component
@Aspect
@Slf4j
public class PagingAspect {

    @Pointcut("within(playground.ws..*)")
    public void withinWS() {
    }

    @Pointcut("@target(org.springframework.stereotype.Service)")
    public void targetService() {
    }

    @Around("withinWS() && targetService() && @annotation(pagingAnnotation)")
    public Object handlePaging(ProceedingJoinPoint pjp, SrvPaging pagingAnnotation) throws Throwable {
        System.out.println("Called....");
        return null;
    }


}
