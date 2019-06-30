package com.tk.annotation.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class MainNamedQueryUser {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		
        Query query = entityMgr.createNamedQuery("find user by id");
        query.setParameter("id", 1L);
        
        List<User> userList = query.getResultList();
        for (User u: userList) {
        	System.out.println(u);
        }
		
		entityMgr.close();
		entityMgrFactory.close();
		
	}

}
