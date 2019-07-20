package com.tk.annotation.inheritance.abstractentities2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("1")
public class FullTimeEmployeeEntity2 extends EmployeeEntity2 {

	private double salary;
}
