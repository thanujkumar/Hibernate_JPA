package playground.high.performance.persistence.support;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import playground.model.Inventory;
import playground.model.Order;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

//https://vladmihalcea.com/improve-statement-caching-efficiency-in-clause-parameter-padding/
@Service("jpaInClauseSupport")
public class JPAInClauseHandlingSupport {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Order> usingInClauseOptimization(Long... values) {
        return entityManager.createQuery("SELECT o FROM Order o where o.orderId in :ids", Order.class)
                .setParameter("ids", Arrays.asList(values))
                .getResultList();
    }
}
