package playground.high.performance.stats;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.spi.StatisticsFactory;
import org.hibernate.stat.spi.StatisticsImplementor;

public class TransactionStatisticsFactory implements StatisticsFactory {

    //<property name="hibernate.stats.factory" value="playground.high.performance.stats.TransactionStatisticsFactory"/>
    @Override
    public StatisticsImplementor buildStatistics(SessionFactoryImplementor sessionFactory) {
        return new TransactionStatistics(sessionFactory);
    }
}