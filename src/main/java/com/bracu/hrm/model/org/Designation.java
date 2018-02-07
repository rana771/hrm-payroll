package com.bracu.hrm.model.org;

import com.bracu.hrm.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "designation")
@Data
public class Designation extends BaseEntity {

	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(name = "short_name", unique = true)
	private String shortName;
	private Integer level;
	
	@Column(name = "functional_name", unique = true)
	private String functionalName;
	
	@Column(name = "functional_short_name", unique = true)
	private String functionalShortName;


	
}
