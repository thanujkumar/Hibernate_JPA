package com.tk.annotation.inheritance.table_per_concrete_class.stratergy2;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class PassengerVehiclePerClassEntity extends VehiclePerClassEntity {

	@Column(name = "NO_OF_PASSENGERS")
	private int noOfPassengers;
}
