package com.tk.annotation.inheritance.nonmappedsuper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
