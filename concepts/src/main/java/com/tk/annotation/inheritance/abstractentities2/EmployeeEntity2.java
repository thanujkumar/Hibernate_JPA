package com.tk.annotation.inheritance.abstractentities2;

import lombok.Data;

import javax.persistence.*;

/**
 * An abstract class may be declared an entity by decorating the class
 * with @Entity. Abstract entities are like concrete entities but cannot be
 * instantiated.
 * 
 * Abstract entities can be queried just like concrete entities. If an abstract
 * entity is the target of a query, the query operates on all the concrete
 * subclasses of the abstract entity
 *
 * Check https://vladmihalcea.com/the-best-way-to-map-the-discriminatorcolumn-with-jpa-and-hibernate/
 *
 * https://docs.oracle.com/cd/B19306_01/gateways.102/b14270/apa.htm (datatypes)
 *
 */
@Entity
@Data
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = "TYPE_ID ",
		columnDefinition = "NUMBER(3)") //Need to provide discriminator value to each class type
// Default discriminatory column DTYPE is used
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Default is Single table
@Table(name="EMPLOYEE_ABSTRACT2")
public abstract class EmployeeEntity2 {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)// would create hibernate_sequence
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_abs_name2")
	@SequenceGenerator(allocationSize = 50, sequenceName = "EMP_ABS_SEQ2", name = "emp_abs_name2")
	@Column(name = "EMP_ID")
	private int employeeId;
}
