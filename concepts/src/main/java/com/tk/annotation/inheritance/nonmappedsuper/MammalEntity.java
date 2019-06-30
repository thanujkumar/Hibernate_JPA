package com.tk.annotation.inheritance.nonmappedsuper;

import lombok.Data;

/**
 * Non-Entity Superclasses Entities may have non-entity superclasses, and these
 * superclasses can be either abstract or concrete. The state of non-entity
 * superclasses is nonpersistent, and any state inherited from the non-entity
 * superclass by an entity class is nonpersistent. Non-entity superclasses may
 * NOT be used in EntityManager or Query operations. Any mapping or relationship
 * annotations in non-entity superclasses are ignored.
 */
@Data
public class MammalEntity {

	private String mammalType; //not persisted
	
	private int lifeSpan; //not persisted
	
}
