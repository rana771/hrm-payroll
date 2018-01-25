package com.bracu.hrm.model.settings;

import com.bracu.hrm.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//Employee type like Permanent, contractual etc

@Entity
@Table(name ="employee_type")
@Data
public class EmployeeType extends BaseEntity{

	@Column(unique=true)
	private String name;

	private String note;

}
