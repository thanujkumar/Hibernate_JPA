package com.tk.annotation.inheritance.joinedtable.stratergy2;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class TransportVehicleJoinedEntity extends VehicleJoinedEntity {

	@Column(name = "LOAD_CAPACITY")
	private int loadCapacity;
}
