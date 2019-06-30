package com.tk.annotation.inheritance.singletable_per_class_hierarchy.stratergy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 
 * <code> Entity Inheritance Mapping Strategies </code> <br>
 * You can configure how the Java Persistence provider maps inherited entities
 * to the underlying datastore by decorating the root class of the hierarchy
 * with the annotation javax.persistence.Inheritance. The following mapping
 * strategies are used to map the entity data to the underlying database:
 * <ol>
 * <li>A single table per class hierarchy
 * 
 * <li>A table per concrete entity class
 * 
 *<li> A <code>join</code> strategy, whereby fields or properties that are specific to a
 * subclass are mapped to a different table than the fields or properties that
 * are common to the parent class
 * </ol>
 * The strategy is configured by setting the strategy element of @Inheritance to
 * one of the options defined in the javax.persistence.InheritanceType
 * enumerated type:
 * 
 * <pre>
 * public enum InheritanceType {
 * 	SINGLE_TABLE, JOINED, TABLE_PER_CLASS
 * };
 * </pre>
 * 
 * The default strategy, InheritanceType.SINGLE_TABLE, is used if
 * the @Inheritance annotation is not specified on the root class of the entity
 * hierarchy.
 * 
 * </br>
 * <i> With this strategy, which corresponds to the default
 * InheritanceType.SINGLE_TABLE, all classes in the hierarchy are mapped to a
 * single table in the database. This table has a discriminator column
 * containing a value that identifies the subclass to which the instance
 * represented by the row belongs.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="STAFF")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public class StaffEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STAFF_ID")
	private Long staffId;

	@Column(name="NAME")
    private String name;
	
	
}
