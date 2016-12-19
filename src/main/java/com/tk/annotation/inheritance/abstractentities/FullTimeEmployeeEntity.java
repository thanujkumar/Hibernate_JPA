package com.tk.annotation.inheritance.abstractentities;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class FullTimeEmployeeEntity extends EmployeeEntity {

	private double salary;
}
