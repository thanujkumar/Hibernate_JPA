package com.tk.annotation.inheritance.singletable_per_class_hierarchy.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class TransportVehicleEntity extends VehicleEntity {

	@Column(name = "LOAD_CAPACITY")
	private int loadCapacity;
}
