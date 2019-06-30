package com.tk.annotation.inheritance.joinedtable.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class PassengerVehicleJoinedEntity extends VehicleJoinedEntity {

	@Column(name = "NO_OF_PASSENGERS")
	private int noOfPassengers;
}
