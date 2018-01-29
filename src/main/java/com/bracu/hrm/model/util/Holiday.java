package com.bracu.hrm.model.util;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bracu.hrm.model.BaseEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "holiday")
@Data
public class Holiday extends BaseEntity {
	
	@ManyToOne(targetEntity = HrYear.class, fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinColumn(name = "hr_year_id")
	private HrYear hrYear;
	
	@Column(unique=true,nullable=false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date date;
	
	private String name;
	
	private String description;


	@Column(name="is_repeated")
	private Boolean isRepeated;		// if true then insert repeated values in multiple rows
	
	
}
