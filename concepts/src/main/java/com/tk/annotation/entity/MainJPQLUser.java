package com.tk.annotation.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class MainJPQLUser {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();

		//scalar function
		Query query = entityMgr.createQuery("select LOWER(u.firstName) from User u");
		List<String> nameList = query.getResultList();
		for (String s: nameList) {
			System.out.println("First Name :-"+ s);
		}
		
		//Aggregate function
		Query query2 = entityMgr.createQuery("select MAX(u.salary) from User u");
		Double total = (Double) query2.getSingleResult();
		System.out.println("Max salary :-"+ total);
		
		//Between function
		Query query3 = entityMgr.createQuery("select u from User u where u.salary between 10 and 300");
		List<User> userList = query3.getResultList();
		for (User u : userList) {
			System.out.println(u);
		}
		
		entityMgr.close();
		entityMgrFactory.close();

	}

}
