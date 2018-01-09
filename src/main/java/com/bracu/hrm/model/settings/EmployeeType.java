package com.bracu.hrm.model.settings;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bracu.hrm.model.User;

//Employee type like Permanent, contractual etc

@Entity
@Table(name ="employee_type")
public class EmployeeType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
	
	private String name;


	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "created_user_id")
	private User userCreated;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "updated_user_id")
	private User userLastUpdated;
	
	
	@Column(name = "date_created")
	private Date dateCreated;
	
	@Column(name = "date_updated")
	private Date dateLastUpdated;
}
