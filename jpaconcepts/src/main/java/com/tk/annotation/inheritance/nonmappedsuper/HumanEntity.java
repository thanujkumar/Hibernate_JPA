package com.tk.annotation.inheritance.nonmappedsuper;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@Table(name = "HUMAN")
@EqualsAndHashCode(callSuper = true)
public class HumanEntity extends MammalEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HUMAN_ID")
	private long id;

	private int legs;
}
