package com.tk.annotation.inheritance.joinedtable.stratergy2;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class TruckJoinedEntity extends TransportVehicleJoinedEntity {

	private int noOfContainer;
}
