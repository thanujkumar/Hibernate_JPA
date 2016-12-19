package com.tk.annotation.inheritance.singletable_per_class_hierarchy.stratergy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@DiscriminatorValue(value = "TS")
public class TeachingStaffEntity extends StaffEntity {

	private String qualification;

	private String subjectExperience;

	public TeachingStaffEntity(Long sid, String name, String qualification, String experience) {
        super(sid, name);
        this.qualification = qualification;
        this.subjectExperience = experience;
	}
	
	public TeachingStaffEntity() {
		super();
	}
}
