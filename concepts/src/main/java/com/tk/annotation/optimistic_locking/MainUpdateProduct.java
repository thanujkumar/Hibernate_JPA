package com.tk.annotation.optimistic_locking;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

//RUN after creating products that has generated version
public class MainUpdateProduct {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("LearnJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();


        Product p = entityManager.find(Product.class, 1L ); //Due to @Version Entity Manager choose right lock mode
        System.out.println("Lock Mode Choosen : "+ entityManager.getLockMode(p));

        p.setQuantity(5);
        //entityManager.persist(p); // without calling persist product is be persisted as in the session object is
        // modified (you should consider detach do some operation and then save)

        tx.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
