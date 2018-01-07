package com.bracu.hrm.model.util;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "holiday")
public class Holiday {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
	
	@Column(unique=true,nullable=false)
	private Date date;
	
	private String name;
	
	private String description;
	
}
