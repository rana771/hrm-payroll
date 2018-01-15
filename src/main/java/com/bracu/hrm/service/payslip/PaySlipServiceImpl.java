package com.bracu.hrm.service.payslip;

import com.bracu.hrm.dao.CompanyDao;
import com.bracu.hrm.dao.PaySlipDao;
import com.bracu.hrm.model.org.Company;
import com.bracu.hrm.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


@Service("paySlipService")
@Transactional
public class PaySlipServiceImpl implements PaySlipService {

	@Autowired
	private PaySlipDao paySlipDao;
	
//	public Company findById(int id) {
//		return companyDao.findById(id);
//	}
//
//	public Company findByName(String name) {
//		Company company = companyDao.findByName(name);
//		return company;
//	}
//
//	public void save(Company company) {
//		companyDao.save(company);
//	}
//
//	public void update(Company company) {
//		Company entity = companyDao.findById(company.getId());
//		if(entity!=null){
//			entity.setVersion(company.getVersion()+1);
//			entity.setName(company.getName());
//			entity.setShortName(company.getShortName());
//			entity.setEmail(company.getEmail());
//			entity.setWebUrl(company.getWebUrl());
//			entity.setEmail(company.getEmail());
//			entity.setPhone1(company.getPhone1());
//			entity.setPhone2(company.getPhone2());
//			entity.setFax(company.getFax());
//			entity.setAddress(company.getAddress());
//			entity.setCompanyLogo(company.getCompanyLogo());
//		}
//	}
//
//
//	public void delete(int id) {
//		companyDao.delete(id);
//	}

	public ResultSet findAll(Map params) {
		return paySlipDao.findAll(params);
	}

//	public boolean isUnique(Integer id, String name) {
//		Company user = findByName(name);
//		return ( user == null || ((id != null) && (user.getId() == id)));
//	}



}
