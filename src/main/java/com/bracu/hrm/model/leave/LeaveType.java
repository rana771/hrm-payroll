package com.bracu.hrm.model.leave;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bracu.hrm.model.BaseEntity;

@Entity
@Table(name = "leave_type")
public class LeaveType extends BaseEntity {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
