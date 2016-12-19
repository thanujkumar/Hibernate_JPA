package com.tk.annotation.inheritance.singletable_per_class_hierarchy.stratergy2;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = "BIKE")
public class BikeEntity extends PassengerVehicleEntity {

	private int saddleHeight;
}
