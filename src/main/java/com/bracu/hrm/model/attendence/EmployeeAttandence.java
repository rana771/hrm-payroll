package com.bracu.hrm.model.attendence;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bracu.hrm.model.Employee;

@Entity
@Table(name = "employee_attendance")
public class EmployeeAttandence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
	
	@ManyToOne(targetEntity=Employee.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="empoyee_id")
	private Employee employee;
	
	private Date date;
	
	private String startTime;
	private String endTIme;
	
	
}
