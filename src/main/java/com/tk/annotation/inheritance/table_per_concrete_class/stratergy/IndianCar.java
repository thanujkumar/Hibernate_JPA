package com.tk.annotation.inheritance.table_per_concrete_class.stratergy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

/**
 * 
 * In this strategy, which corresponds to InheritanceType.TABLE_PER_CLASS, each
 * concrete class is mapped to a separate table in the database. All fields or
 * properties in the class, including inherited fields or properties, are mapped
 * to columns in the class’s table in the database.
 * 
 * This strategy provides poor support for polymorphic relationships and usually
 * requires either SQL UNION queries or separate SQL queries for each subclass
 * for queries that cover the entire entity class hierarchy.
 * 
 * Support for this strategy is optional and may not be supported by all Java
 * Persistence API providers. The default Java Persistence API provider in the
 * GlassFish Server does not support this strategy.*
 */
/*
 * Look why global temp table HT_ are created?
 * 
 * http://in.relation.to/2005/07/20/multitable-bulk-operations/
 */
@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class IndianCar {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAR_ID")
	private int vehicleId;

	@Column(name = "MANUFACTURER")
	private String manufacturer;

	@Column(name = "PRICE")
	private double price;

}
