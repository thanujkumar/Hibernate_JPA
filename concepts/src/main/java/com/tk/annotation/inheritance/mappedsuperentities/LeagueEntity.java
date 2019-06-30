package com.tk.annotation.inheritance.mappedsuperentities;

import lombok.Data;

import javax.persistence.*;

/**
 * Entities may inherit from superclasses that contain persistent state and
 * mapping information but are not entities. That is, the superclass is not
 * decorated with the @Entity annotation and is not mapped as an entity by the
 * Java Persistence provider. These superclasses are most often used when you
 * have state and mapping information common to multiple entity classes.
 * 
 * Mapped superclasses are specified by decorating the class with the annotation
 * javax.persistence.MappedSuperclass
 * 
 * 
 * Mapped superclasses cannot be queried and can't be used in EntityManager or
 * Query operations. You must use entity subclasses of the mapped superclass in
 * EntityManager or Query operations. Mapped superclasses can't be targets of
 * entity relationships. Mapped superclasses can be abstract or concrete.
 * 
 * Mapped superclasses do not have any corresponding tables in the underlying
 * datastore. Entities that inherit from the mapped superclass define the table
 * mappings. For instance, in the preceding code sample, the underlying tables
 * would be SUMMER_LEAGUE and WINTER_LEAGUE, but there is no LEAGUE
 * table.
 */
@Data
@MappedSuperclass
public class LeagueEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "league_super_name")
	@SequenceGenerator(allocationSize = 1, sequenceName = "LEAGUE_SUPER_SEQ", name = "league_super_name")
	@Column(name = "LEAUGE_ID")
	private int leagueId;
}
