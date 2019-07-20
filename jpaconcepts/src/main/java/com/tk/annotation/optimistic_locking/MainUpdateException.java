package com.tk.annotation.optimistic_locking;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MainUpdateException extends Logging {

    private static CountDownLatch countDownLatch = new CountDownLatch(2); //2 threads wait to sync and continue for
    // update

    public static void main(String[] args) {
        //Here two threads will be created both will read and wait for the other to commit and next one try
        Thread t1 = new Thread(new UpdateThread());
        Thread t2 = new Thread(new UpdateThread());
        t1.start();
        t2.start();


    }


    private static class UpdateThread implements Runnable {

        @Override
        public void run() {
            EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
            EntityManager entityMgr = entityMgrFactory.createEntityManager();
            entityMgr.getTransaction().begin();
            //Read
            Product p = entityMgr.find(Product.class, 1L);

            //wait for other thread to read
            countDownLatch.countDown();//decrease by 1
            try {
                countDownLatch.await();//wait till count is decreased to 0 (by other thread reading)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random rand = new Random();//If same value set no change is object state and will not be persisited

            //Now perform update as one of the threads will succeed and other fails
            p.setQuantity(rand.nextInt(10)); // as in session will be committed automatically
            try {
                entityMgr.getTransaction().commit();
            } catch (Exception e) {
                //Runtime exception
                e.printStackTrace();//Catching this so closing of the resources is proper
            }
            entityMgr.close();
            entityMgrFactory.close();
        }
    }
}
