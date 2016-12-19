package com.tk.annotation.inheritance.mappedsuperentities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "WINTER_LEAGUE")
@EqualsAndHashCode(callSuper = true)
public class WinterLeagueEntity extends LeagueEntity {

	private float hourlyWage;

}
