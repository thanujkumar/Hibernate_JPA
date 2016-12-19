package com.tk.annotation.inheritance.nonmappedsuper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "DOG")
@EqualsAndHashCode(callSuper = true)
public class DogEntity extends MammalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DOG_ID")
	private long id;

	private int legs;

}
