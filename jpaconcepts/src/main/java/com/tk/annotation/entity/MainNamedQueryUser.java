package com.tk.annotation.entity;

import com.tk.log.Logging;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class MainNamedQueryUser extends Logging {

	public static void main(String[] args) {
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();
		entityMgr.getTransaction().begin();

        Query query = entityMgr.createNamedQuery("find user by id");
        query.setParameter("id", 1L);
        
        List<User> userList = query.getResultList();
        for (User u: userList) {
        	System.out.println(u);
        }

		entityMgr.getTransaction().commit();
		entityMgr.close();
		entityMgrFactory.close();
		
	}

}
