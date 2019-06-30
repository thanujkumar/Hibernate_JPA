package com.tk.annotation.inheritance.abstractentities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PartTimeEmployeeEntity extends EmployeeEntity {

	private float hourlyWage;

}
