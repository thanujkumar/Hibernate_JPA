package com.tk.annotation.inheritance.abstractentities;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class PartTimeEmployeeEntity extends EmployeeEntity {

	private float hourlyWage;

}
