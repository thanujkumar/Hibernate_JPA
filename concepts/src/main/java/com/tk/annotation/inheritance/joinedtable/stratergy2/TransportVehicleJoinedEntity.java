package com.tk.annotation.inheritance.joinedtable.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public abstract class TransportVehicleJoinedEntity extends VehicleJoinedEntity {

	@Column(name = "LOAD_CAPACITY")
	private int loadCapacity;
}
