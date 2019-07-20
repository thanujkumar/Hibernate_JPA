package com.tk.annotation.inheritance.nonmappedsuper;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainMammal extends Logging {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		EntityTransaction tx = entityMgr.getTransaction();
		
		tx.begin();
		
		HumanEntity hu = new HumanEntity();
		hu.setLegs(2);
		hu.setLifeSpan(80);
		hu.setMammalType("HUMAN");
		
		DogEntity dg = new DogEntity();
		dg.setLegs(4);
		dg.setMammalType("DOG");
		dg.setLifeSpan(12);
				
		entityMgr.persist(hu);
		entityMgr.persist(dg);
		
		tx.commit();
		
		entityMgr.close();
		entityMgrFactory.close();
	}

}
