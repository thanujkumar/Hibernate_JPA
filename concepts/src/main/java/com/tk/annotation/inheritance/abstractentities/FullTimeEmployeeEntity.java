package com.tk.annotation.inheritance.abstractentities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class FullTimeEmployeeEntity extends EmployeeEntity {

	private double salary;
}
