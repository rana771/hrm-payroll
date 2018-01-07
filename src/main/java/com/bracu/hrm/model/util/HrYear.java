package com.bracu.hrm.model.util;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "hr_year")
public class HrYear {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
	
	private Date dateFrom;
	private Date dateTo;
	
	private Boolean isOpen;
	private Boolean isClosed;
	
}
