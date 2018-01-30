package com.bracu.hrm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bracu.hrm.model.settings.SetupEntity;
import lombok.Data;

@Entity
@Table(name = "employee_contact")
@Data
public class EmployeeContact extends BaseEntity {

	@ManyToOne(targetEntity=Employee.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="empoyee_id")
	private Employee employee;

	@ManyToOne(targetEntity=SetupEntity.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="contact_type_id")
	private SetupEntity contactType;
	
	private String phone;
	
	private String mobile;
	
	private String email;
	
	private String fax;
	
	private boolean isActive;
	
}
