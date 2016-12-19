package com.tk.annotation.inheritance.singletable_per_class_hierarchy.stratergy2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
 * Now there are four tables, Vehicle, Truck, Car and Bike. 
 * As the name JPA joined inheritance example obviously suggests,
 * they need to be joined so that inheritance relationship is maintained.
 */
public class MainVehicleStratergy {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();

		EntityTransaction tx = entityMgr.getTransaction();
		tx.begin();

		BikeEntity bk1 = new BikeEntity();
		bk1.setManufacturer("honda");
		bk1.setNoOfPassengers(1);
		bk1.setSaddleHeight(30);
		entityMgr.persist(bk1);

		CarEntity car1 = new CarEntity();
		car1.setManufacturer("lamborghini");
		car1.setNoOfDoors(2);
		car1.setNoOfPassengers(2);
		entityMgr.persist(car1);

		TruckEntity truck1 = new TruckEntity();
		truck1.setLoadCapacity(100);
		truck1.setManufacturer("mercedes");
		truck1.setNoOfContainer(2);
		entityMgr.persist(truck1);

		tx.commit();
		entityMgr.close();
		entityMgrFactory.close();
	}
}
