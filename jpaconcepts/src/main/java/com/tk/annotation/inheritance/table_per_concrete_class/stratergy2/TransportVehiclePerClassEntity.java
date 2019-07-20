package com.tk.annotation.inheritance.table_per_concrete_class.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class TransportVehiclePerClassEntity extends VehiclePerClassEntity {

	@Column(name = "LOAD_CAPACITY")
	private int loadCapacity;
}
