package com.tk.annotation.inheritance.singletable_per_class_hierarchy.stratergy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MainSingleTableStaff {

	public static void main(String[] args) {
		
		EntityManagerFactory entityMgrFactory = Persistence.createEntityManagerFactory("LearnJPA");
		EntityManager entityMgr = entityMgrFactory.createEntityManager();

		entityMgr.getTransaction().begin();
		
		//Teaching staff
		TeachingStaffEntity ts1 = new TeachingStaffEntity(1L, "Gopal", "MSc MEd", "Maths");
		TeachingStaffEntity ts2 = new TeachingStaffEntity(2L, "Manisha", "BSc BEd", "English");
		
		//Non-Teaching Staff entity
	    NonTeachingStaffEntity nts1=new NonTeachingStaffEntity(3L, "Satish", "Accounts");
	    NonTeachingStaffEntity nts2=new NonTeachingStaffEntity(4L, "Krishna", "Office Admin");

	    //storing all entities
	    entityMgr.merge(ts1);
	    entityMgr.merge(ts2);
	    entityMgr.merge(nts1);
	    entityMgr.merge(nts2);
	      
	    entityMgr.getTransaction().commit();
	      
		entityMgr.close();
		entityMgrFactory.close();
	}
}
