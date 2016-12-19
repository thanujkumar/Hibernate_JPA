package com.tk.annotation.inheritance.table_per_concrete_class.stratergy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainIndianCar {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();

		EntityTransaction tx = entityMgr.getTransaction();
		tx.begin();
		
		MarutiCarEntity mc = new MarutiCarEntity();
		mc.setManufacturer("Maruti Suzuki");
		mc.setOwner("VINUTHA R");
		mc.setPrice(600000.00);
		
		entityMgr.persist(mc);
		tx.commit();
		
        tx.begin();		 
		HyundaiCarEntity hc = new HyundaiCarEntity();
		hc.setManufacturer("Hyundai");
		hc.setOwner("EVRY INDIA");
		hc.setPrice(500000.00);
		
		entityMgr.persist(hc);
		tx.commit();
		 
		entityMgr.close();
		entityMgrFactory.close();
	}
}
