package com.tk.annotation.inheritance.abstractentities2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainEmployeeAbstract2 {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		EntityTransaction tx = entityMgr.getTransaction();
		
		tx.begin();
		
		FullTimeEmployeeEntity2 ft = new FullTimeEmployeeEntity2();
		ft.setSalary(245.8D);
		
		PartTimeEmployeeEntity2 pt = new PartTimeEmployeeEntity2();
		pt.setHourlyWage(12.35F);
				
		entityMgr.persist(ft);
		entityMgr.persist(pt);
		
		tx.commit();
		
		entityMgr.close();
		entityMgrFactory.close();
	}

}
