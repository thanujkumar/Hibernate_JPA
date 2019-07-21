package playground.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import playground.model.Region;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RegionDAO {

    //Injects shared EntityManager by spring
    @PersistenceContext
    private EntityManager entityManager;

    public List<Region> findAll() {
        System.out.println("RegionDAO.findAll Hibernate Flush Mode : "+ entityManager.unwrap(Session.class).getHibernateFlushMode());
        System.out.println("RegionDAO.findAll Session IsReadOnly : "+ entityManager.unwrap(Session.class).isDefaultReadOnly());
        System.out.println("RegionDAO.findAll TxSync isSyncActive : "+ TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("RegionDAO.findAll TxSync isReadOnly : "+ TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.println("RegionDAO.findAll TxSync isTxActive : "+ TransactionSynchronizationManager.isActualTransactionActive());
        return entityManager.createNamedQuery(Region.QUERY_ALL).getResultList();
    }

    public void save(Region region) {
        System.out.println("RegionDAO.save Hibernate Flush Mode : "+ entityManager.unwrap(Session.class).getHibernateFlushMode());
        System.out.println("RegionDAO.save Session IsReadOnly : "+ entityManager.unwrap(Session.class).isDefaultReadOnly());
        System.out.println("RegionDAO.save TxSync isSyncActive : "+ TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("RegionDAO.save TxSync isReadOnly : "+ TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.println("RegionDAO.save TxSync isTxActive : "+ TransactionSynchronizationManager.isActualTransactionActive());
        entityManager.persist(region);
    }
}
