package com.tk.annotation.inheritance.mappedsuperentities;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainLeagueSuper extends Logging {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		EntityTransaction tx = entityMgr.getTransaction();
		
		tx.begin();
		
		SummerLeagueEntity ft = new SummerLeagueEntity();
		ft.setSalary(245.8D);
		
		WinterLeagueEntity pt = new WinterLeagueEntity();
		pt.setHourlyWage(12.35F);
				
		entityMgr.persist(ft);
		entityMgr.persist(pt);
		
		tx.commit();
		
		entityMgr.close();
		entityMgrFactory.close();
	}

}
