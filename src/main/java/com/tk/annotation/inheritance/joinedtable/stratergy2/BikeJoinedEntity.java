package com.tk.annotation.inheritance.joinedtable.stratergy2;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class BikeJoinedEntity extends PassengerVehicleJoinedEntity {

	private int saddleHeight;

//	@Override
//	@OneToOne
//	@JoinColumn(name="VEHICLE_ID", referencedColumnName="VEHICLE_ID", foreignKey=@ForeignKey(name="FK_VEHICLE_JOINED_BIKE"))
//	public int getVehicleId() {
//		return super.getVehicleId();
//	}
}
