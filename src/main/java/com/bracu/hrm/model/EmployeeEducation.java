package com.bracu.hrm.model;

import com.bracu.hrm.model.settings.SetupEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "employee_education")
@Data
public class EmployeeEducation extends BaseEntity {
	
	@ManyToOne(targetEntity=Employee.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="empoyee_id")
	private Employee employee;
	
	
	@ManyToOne(targetEntity=SetupEntity.class,fetch=FetchType.LAZY,cascade= CascadeType.DETACH)
	@JoinColumn(name="education_title_id")
	private SetupEntity educationTitle;
	
	private String board; // board / univeristy
	
	private String institute;
	
	private String result;
	
	private Integer passingYear;
	@Transient
	private MultipartFile certificatedata;
	@Column(nullable = true)
	private byte[] certificate;

	
}
