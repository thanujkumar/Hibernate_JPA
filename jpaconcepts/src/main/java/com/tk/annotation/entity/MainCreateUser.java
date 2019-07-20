package com.tk.annotation.entity;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainCreateUser extends Logging {
	
	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		
		User user = new User();
		user.setFirstName("THANUJ KUMAR");
		user.setLastName("SATHYAPPA CHOWDAPPA");
		user.setSalary(234.89D);
		
		entityMgr.getTransaction().begin();
		
		entityMgr.persist(user);

		entityMgr.getTransaction().commit();

		entityMgr.getTransaction().begin();
		
		User user2 = new User();
		user2.setFirstName("AJEETH");
		user2.setLastName("BT");
		user2.setSalary(34.89D);

		entityMgr.persist(user2);

		entityMgr.getTransaction().commit();
		
		entityMgr.close();
		entityMgrFactory.close();
		
	}

}
