package com.tk.annotation.inheritance.abstractentities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * An abstract class may be declared an entity by decorating the class
 * with @Entity. Abstract entities are like concrete entities but cannot be
 * instantiated.
 * 
 * Abstract entities can be queried just like concrete entities. If an abstract
 * entity is the target of a query, the query operates on all the concrete
 * subclasses of the abstract entity
 * 
 */
@Entity
@Data
// @DiscriminatorColumn(name = "DTYPE ")
// Default discriminatory column DTYPE is used
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Default is Single table
@Table(name="EMPLOYEE_ABSTRACT")
public abstract class EmployeeEntity {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)// would create hibernate_sequence
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_abs_name")
	@SequenceGenerator(allocationSize = 50, sequenceName = "EMP_ABS_SEQ", name = "emp_abs_name")
	@Column(name = "EMP_ID")
	private int employeeId;
}
