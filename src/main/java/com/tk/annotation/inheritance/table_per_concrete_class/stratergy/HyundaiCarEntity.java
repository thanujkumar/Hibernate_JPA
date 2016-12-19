package com.tk.annotation.inheritance.table_per_concrete_class.stratergy;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "HYUNDAI_CAR")
public class HyundaiCarEntity extends IndianCar {

	private String owner;
}
