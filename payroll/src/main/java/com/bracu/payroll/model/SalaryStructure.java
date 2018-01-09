package com.bracu.payroll.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bracu.hrm.model.org.Designation;
import com.bracu.hrm.model.settings.EmployeeType;
import com.bracu.hrm.model.settings.SetupEntity;


@Entity
@Table(name = "employee")
public class SalaryStructure {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
	
	@ManyToOne(targetEntity=Designation.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="designation_id")
	private Designation designation;
	
	@ManyToOne(targetEntity=EmployeeType.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="employee_type_id")
	private EmployeeType employeeType;
	
	
	private double minSalary;
	
	
}
