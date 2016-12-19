package com.tk.annotation.inheritance.abstractentities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainEmployeeAbstract {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		EntityTransaction tx = entityMgr.getTransaction();
		
		tx.begin();
		
		FullTimeEmployeeEntity ft = new FullTimeEmployeeEntity();
		ft.setSalary(245.8D);
		
		PartTimeEmployeeEntity pt = new PartTimeEmployeeEntity();
		pt.setHourlyWage(12.35F);
				
		entityMgr.persist(ft);
		entityMgr.persist(pt);
		
		tx.commit();
		
		entityMgr.close();
		entityMgrFactory.close();
	}

}
