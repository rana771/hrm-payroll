package com.bracu.hrm.service.payslip;

import com.bracu.hrm.model.org.Company;
import org.springframework.web.servlet.ModelAndView;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


public interface PaySlipService {
	

	ResultSet findAll(Map params);
	ModelAndView generatePaySlip(Map params);
	

}