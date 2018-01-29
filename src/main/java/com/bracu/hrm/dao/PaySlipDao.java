package com.bracu.hrm.dao;

import com.bracu.hrm.model.org.Company;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface PaySlipDao {


	ResultSet findAll(Map params);
	List getRequisitionList();

}

