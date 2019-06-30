package com.tk.annotation.optimistic_locking;

import org.hibernate.procedure.ProcedureOutputs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.stream.IntStream;

public class MainCreateProducts {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("LearnJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();


        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();



        IntStream.range(1, 10).forEach(x -> {
            Product product  = new Product();
            product.setProductName("ProdName"+x);
            product.setQuantity(10);
            entityManager.persist(product);
        });

        tx.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
