package com.tk.annotation.inheritance.joinedtable.stratergy2;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainVehicleJoinedStratergy extends Logging {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();

		EntityTransaction tx = entityMgr.getTransaction();
		tx.begin();
		
		BikeJoinedEntity bk1 = new BikeJoinedEntity();
		bk1.setManufacturer("honda");
		bk1.setNoOfPassengers(1);
		bk1.setSaddleHeight(30);
		entityMgr.persist(bk1);
		 
		CarJoinedEntity car1 = new CarJoinedEntity();
		car1.setManufacturer("lamborghini");
		car1.setNoOfDoors(2);
		car1.setNoOfPassengers(2);
		entityMgr.persist(car1);
		 
		TruckJoinedEntity truck1 = new TruckJoinedEntity();
		truck1.setLoadCapacity(100);
		truck1.setManufacturer("mercedes");
		truck1.setNoOfContainer(2);
		entityMgr.persist(truck1);
		
		tx.commit();
		entityMgr.close();
		entityMgrFactory.close();
	}
}
