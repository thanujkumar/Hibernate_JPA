package com.tk.annotation.inheritance.table_per_concrete_class.stratergy;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "MARUTI_CAR")
public class MarutiCarEntity extends IndianCar {

	private String owner;
}
