package com.tk.annotation.inheritance.joinedtable.stratergy2;

import com.tk.log.Logging;

import javax.persistence.*;
import java.util.List;

public class MainVehicleRead extends Logging {
    public static void main(String[] args) {
        EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
        EntityManager entityMgr = entityMgrFactory.createEntityManager();

        EntityTransaction tx = entityMgr.getTransaction();
        tx.begin();

        Query query1 = entityMgr.createQuery("select u from BikeJoinedEntity u ");
        List<BikeJoinedEntity> bikes = query1.getResultList();
        for (BikeJoinedEntity u : bikes) {
            System.out.println(u);
        }

        //Get all vehicles

        Query query2 = entityMgr.createQuery("select u from CarJoinedEntity u ");
        List<VehicleJoinedEntity> cars = query2.getResultList();
        for (VehicleJoinedEntity u : cars) {
            System.out.println(u);
        }

        entityMgr.close();
        entityMgrFactory.close();

    }
}
