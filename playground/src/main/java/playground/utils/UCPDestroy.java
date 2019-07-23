package playground.utils;

import oracle.ucp.UniversalConnectionPool;
import oracle.ucp.admin.UniversalConnectionPoolManagerImpl;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

@Component
public class UCPDestroy implements DisposableBean {


    public void destroy() throws Exception {
        String[] names = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().getConnectionPoolNames();
        for (String name : names) {
            UniversalConnectionPool ds = UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().getConnectionPool(name);
            System.out.println("----------------------------------------------------------");
            System.out.println("PoolName:" + name);
            System.out.println(ds.getStatistics());
            System.out.println("----------------------------------------------------------");
            UniversalConnectionPoolManagerImpl.getUniversalConnectionPoolManager().destroyConnectionPool(name);
        }
    }
}