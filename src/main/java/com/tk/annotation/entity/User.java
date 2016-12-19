package com.tk.annotation.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames=true, doNotUseGetters=true)
@Entity
@Table(name="USER1")
@NamedQuery(name="find user by id", query="select u from User u where u.userId= :id")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Long userId;
	
	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="SALARY")
	private double salary;
		
}
