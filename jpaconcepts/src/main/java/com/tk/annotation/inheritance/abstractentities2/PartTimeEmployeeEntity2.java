package com.tk.annotation.inheritance.abstractentities2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("2")
public class PartTimeEmployeeEntity2 extends EmployeeEntity2 {

	private float hourlyWage;

}
