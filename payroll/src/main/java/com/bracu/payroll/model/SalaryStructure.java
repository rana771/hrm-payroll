package com.bracu.payroll.model;

import java.util.Date;

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

import com.bracu.hrm.model.User;
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
	@JoinColumn(name = "designation_id")
	private Designation designation;
	
	@ManyToOne(targetEntity=EmployeeType.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name = "employee_type_id")
	private EmployeeType employeeType;
	
	@Column(name = "min_salary")
	private double minSalary;
	
	@Column(name = "max_salary")
	private double maxSalary;
	
	@Column(name = "date_active_from")
	private Date dateActiveFrom;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "created_user_id")
	private User userCreated;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "updated_user_id")
	private User userLastUpdated;
	
	
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Column(name = "date_updated")
	private Date dateLastUpdated;
}
