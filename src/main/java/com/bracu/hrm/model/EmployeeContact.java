package com.bracu.hrm.model;

import com.bracu.hrm.model.settings.SetupEntity;
import lombok.Data;

import javax.persistence.*;

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
