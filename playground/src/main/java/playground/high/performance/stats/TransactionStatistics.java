package playground.high.performance.stats;

import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.stat.internal.StatisticsImpl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionStatistics extends StatisticsImpl {

    private ConcurrentMap<Long, Long> transactionStartNanos = new ConcurrentHashMap<>();
    private ConcurrentMap<Long, AtomicLong> connectionCounter = new ConcurrentHashMap<>();
    private StatisticsReport report = new StatisticsReport();

    public TransactionStatistics(SessionFactoryImplementor sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void connect() {
        long threadId = Thread.currentThread().getId();
        AtomicLong counter = connectionCounter.get(threadId);
        if (counter == null) {
            counter = new AtomicLong();
            connectionCounter.put(threadId, counter);
        }
        counter.incrementAndGet();
        transactionStartNanos.putIfAbsent(threadId, System.nanoTime());
        super.connect();
    }

    @Override
    public void endTransaction(boolean success) {
        long threadId = Thread.currentThread().getId();
        Long startNanos = transactionStartNanos.remove(threadId);
        if (startNanos != null) {
            report.transactionTime(System.nanoTime() - startNanos);
        }
        AtomicLong connectionCounter = this.connectionCounter.remove(threadId);
        if (connectionCounter != null) {
            report.connectionsCount(connectionCounter.longValue());
        }
        report.generate();
        super.endTransaction(success);
    }
}
