package com.tk.annotation.entity;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainUpdateUser extends Logging {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		
		User user;
		entityMgr.getTransaction().begin();
		
		user = entityMgr.find(User.class, 1L);
		
		//NOTE: In a session state is modified and hence it would persist one out from commit
		user.setLastName("SC");
		
		entityMgr.getTransaction().commit();
		
		entityMgr.close();
		entityMgrFactory.close();
		
	}
}
