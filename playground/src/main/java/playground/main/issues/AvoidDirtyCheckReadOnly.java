package playground.main.issues;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import playground.main.Logging;
import playground.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transaction;
import java.util.List;

//https://thoughts-on-java.org/11-jpa-hibernate-query-hints-every-developer-know/
//https://vladmihalcea.com/how-does-the-auto-flush-work-in-jpa-and-hibernate/

/**
 * All database statements are executed within the context of a physical transaction, even when we don’t explicitly declare transaction boundaries (BEGIN/COMMIT/ROLLBACK).
 * <p>
 * If you don't declare transaction boundaries explicitly, then each statement will have to be executed in a separate transaction (autocommit mode [commit=true]).
 * This may even lead to opening and closing one connection per statement unless your environment can deal with connection-per-thread binding.
 * <p>
 * Declaring a service as @Transactional will give you one connection for the whole transaction duration, and all statements will use that
 * single isolation connection. This is way better than not using explicit transactions in the first place.
 * <p>
 * On large applications, you may have many concurrent requests, and reducing database connection acquisition request rate will definitely
 * improve your overall application performance.
 * <p>
 * JPA doesn't enforce transactions on read operations. Only writes end up throwing a transaction required exception in case you
 * forget to start a transactional context. Nevertheless, it's always better to declare transaction boundaries even for read-only
 * transactions (in Spring @Transactional allows you to mark read-only transactions, which has a great performance benefit).
 */
public class AvoidDirtyCheckReadOnly extends Logging {
    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("PLAYGROUND");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();
        entityMgr.unwrap(Session.class).setDefaultReadOnly(true); //For complete session and below is at query level
        entityMgr.unwrap(Session.class).setHibernateFlushMode(FlushMode.MANUAL); //FlushMode.MANUAL is required to stop dirty check (flush)

        //hibernate.connection.provider_disables_autocommit
        EntityTransaction entityTransaction = entityMgr.getTransaction();
        entityTransaction.begin(); //TODO : when read only don't use transaction to avoid flush (dirty check)

        List<Product> productList = entityMgr.createQuery("select p from Product p", Product.class).getResultList();
        //List<Product> productList = entityMgr.createQuery("select p from Product p", Product.class).setHint("org.hibernate.flushMode", FlushMode.MANUAL).getResultList();
        //List<Product> productList = entityMgr.createQuery("select p from Product p", Product.class).setHint("org.hibernate.readOnly", Boolean.TRUE).getResultList();

        for (Product p : productList) {
            System.out.println(p.getProductName());
        }

        entityMgr.getTransaction().commit();

        //entityMgr.unwrap(Session.class).flush();//When not in transaction flush can't be used -javax.persistence.TransactionRequiredException: no transaction is in progress
        System.out.println("------------------------------------------------------");
        System.out.println("Flush Mode : " + entityMgr.unwrap(Session.class).getHibernateFlushMode() + " , Default ReadOnly :" + entityMgr.unwrap(Session.class).isDefaultReadOnly());
        System.out.println("------------------------------------------------------");

        entityMgr.close();
        entityMgrFactory.close();

    }
}
