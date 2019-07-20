package com.tk.annotation.inheritance.nonmappedsuper;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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
