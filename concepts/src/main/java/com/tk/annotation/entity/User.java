package com.tk.annotation.entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

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
