package com.bracu.hrm.model.org;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private Integer version;
	
	@NotEmpty
	@Column(name = "name", nullable = false,unique = true)
	private String name;
	@NotEmpty
	@Column(name = "short_name", nullable = false, unique = true)
	private String shortName;
	@NotEmpty
	@Column(name = "web_url", nullable = false)
	private String webUrl;

	@NotEmpty
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotEmpty
	@Column(name = "phone1", nullable = false)
	private String phone1;

	private String phone2;
	private String fax;
	private String address;
	@Column(name = "company_logo")
	byte[] companyLogo;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public byte[] getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(byte[] companyLogo) {
		this.companyLogo = companyLogo;
	}
	
	
	
}
