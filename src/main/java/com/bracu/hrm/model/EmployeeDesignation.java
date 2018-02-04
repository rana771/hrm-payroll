package com.bracu.hrm.model;

import java.util.Date;

import javax.persistence.*;

import com.bracu.hrm.model.org.Designation;
import com.bracu.hrm.model.settings.SetupEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "employee_designation")
@Data
public class EmployeeDesignation extends BaseEntity{
	
	@ManyToOne(targetEntity=Employee.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	@ManyToOne(targetEntity = Designation.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "designation_id")
	private Designation designation;
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	private Date startDate;
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	
	
}
