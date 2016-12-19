package com.tk.annotation.inheritance.table_per_concrete_class.stratergy2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainVehiclePerClassStratergy {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();

		EntityTransaction tx = entityMgr.getTransaction();
		tx.begin();
		
		BikePerClassEntity bk1 = new BikePerClassEntity();
		bk1.setManufacturer("honda");
		bk1.setNoOfPassengers(1);
		bk1.setSaddleHeight(30);
		entityMgr.persist(bk1);
		 
		CarPerClassEntity car1 = new CarPerClassEntity();
		car1.setManufacturer("lamborghini");
		car1.setNoOfDoors(2);
		car1.setNoOfPassengers(2);
		entityMgr.persist(car1);
		 
		TruckPerClassEntity truck1 = new TruckPerClassEntity();
		truck1.setLoadCapacity(100);
		truck1.setManufacturer("mercedes");
		truck1.setNoOfContainer(2);
		entityMgr.persist(truck1);
		
		tx.commit();
		entityMgr.close();
		entityMgrFactory.close();
	}
}
