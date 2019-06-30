package com.tk.annotation.inheritance.table_per_concrete_class.stratergy2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CAR_PER_CLASS")
public class CarPerClassEntity extends PassengerVehiclePerClassEntity {

	private int noOfDoors;

}
