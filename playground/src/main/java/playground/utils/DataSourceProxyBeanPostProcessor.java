package playground.utils;

import net.ttddyy.dsproxy.ExecutionInfo;
import net.ttddyy.dsproxy.QueryInfo;
import net.ttddyy.dsproxy.listener.logging.DefaultQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.logging.SystemOutQueryLoggingListener;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.List;

@Component
public class DataSourceProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
            return factory.getProxy();
        }
        return bean;
    }

    //https://github.com/ttddyy/datasource-proxy-examples/blob/master/spring-javaconfig-example/src/main/java/net/ttddyy/dsproxy/example/Application.java

    private static class ProxyDataSourceInterceptor implements MethodInterceptor {

        private final DataSource dataSource;

        public ProxyDataSourceInterceptor(final DataSource dataSource) {
           // this.dataSource = ProxyDataSourceBuilder.create(dataSource).name("THANUJ-DS").asJson().countQuery().logQueryToSysOut().build();

            //OR
            // use pretty formatted query with multiline enabled
            PrettyQueryEntryCreator creator = new PrettyQueryEntryCreator();
            creator.setMultiline(true);
            SystemOutQueryLoggingListener listener = new SystemOutQueryLoggingListener();
            listener.setQueryLogEntryCreator(creator);

            this.dataSource = ProxyDataSourceBuilder
                    .create(dataSource)
                    .name("THANUJ-DS")
                    .listener(listener)
                    .beforeQuery(new ProxyDataSourceBuilder.SingleQueryExecution() {
                        @Override
                        public void execute(ExecutionInfo executionInfo, List<QueryInfo> list) {
                            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                        }
                    })
//                    .proxyResultSet()  // enable resultset proxy
//                    .afterMethod(executionContext -> {
//                        // print out JDBC API calls to console
//                        Method method = executionContext.getMethod();
//                        Class<?> targetClass = executionContext.getTarget().getClass();
//                        System.out.println("JDBC: " + targetClass.getSimpleName() + "#" + method.getName());
//                    })
                    .afterQuery((execInfo, queryInfoList) -> {
                        System.out.println("Query took " + execInfo.getElapsedTime() + "msec");
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
                    })
                    .build();
        }

        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(), invocation.getMethod().getName());
            if (proxyMethod != null) {
                return proxyMethod.invoke(dataSource, invocation.getArguments());
            }
            return invocation.proceed();
        }
    }


    // use hibernate to format queries
    private static class PrettyQueryEntryCreator extends DefaultQueryLogEntryCreator {
        private Formatter formatter = FormatStyle.BASIC.getFormatter();

        @Override
        protected String formatQuery(String query) {
            return this.formatter.format(query);
        }
    }

}
