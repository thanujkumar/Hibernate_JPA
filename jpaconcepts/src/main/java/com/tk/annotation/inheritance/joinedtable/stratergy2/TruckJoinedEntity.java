package com.tk.annotation.inheritance.joinedtable.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
public class TruckJoinedEntity extends TransportVehicleJoinedEntity {

	private int noOfContainer;
}
