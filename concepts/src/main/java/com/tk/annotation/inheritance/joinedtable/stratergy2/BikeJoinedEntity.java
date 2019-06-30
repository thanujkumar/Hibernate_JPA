package com.tk.annotation.inheritance.joinedtable.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
