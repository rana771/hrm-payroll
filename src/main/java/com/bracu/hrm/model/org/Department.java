package com.bracu.hrm.model.org;

import com.bracu.hrm.model.BaseEntity;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "department")
@Data
public class Department extends BaseEntity {

	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id" )
	private Company company;
	
	@Column(nullable =  false, unique = true)
	private String code;
	
	@Column(nullable =  false, unique = true)
	private String name;
	
	@Column(name = "short_name", unique  = true)
	private String shortName;
	
	
	@ManyToOne(targetEntity = Department.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name =  "parent_department_id",nullable = true)
	private Department department;
	
	
}
