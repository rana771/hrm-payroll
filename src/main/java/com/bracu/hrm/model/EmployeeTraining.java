package com.bracu.hrm.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "employee_training")
@Data
public class EmployeeTraining extends BaseEntity {
	@ManyToOne(targetEntity=Employee.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="empoyee_id")
	private Employee employee;
	
	
	private String title;
	
	private String topic; // subject / topic of training
	
	private String institute;
	
	private String result;
	
	private Integer duration; // duration in days
	
	private Byte certificate;
	
	
}
