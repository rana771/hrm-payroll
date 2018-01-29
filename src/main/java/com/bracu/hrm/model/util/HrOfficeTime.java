package com.bracu.hrm.model.util;

import javax.persistence.*;

import com.bracu.hrm.model.BaseEntity;
import com.bracu.hrm.model.org.Company;
import lombok.Data;

@Entity
@Table(name = "holiday")
@Data
public class HrOfficeTime extends BaseEntity {
	
	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "start_time_24_format")
	private String startTime24Format;

	@Column(name = "end_time_24_format")
	private String endTime24Format;

	@Column(name = "start_time_12_format")
	private String startTime12Format;

	@Column(name = "end_time_12_format")
	private String endTIme12Format;

	@Column(name = "is_regular_shift")
	private Boolean isRegularShift; // only one active regular shift will be exists per company

	@Column(name = "is_active")
	private Boolean isActive;
	
}
