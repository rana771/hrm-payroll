package com.bracu.hrm.service;

import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.settings.SetupEntity;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;


public interface EmployeeService {
	
	Employee findById(int id);
	Employee findByPin(String pin);
	void saveEmployee(Employee employee);
	void updateEmployee(Employee employee);
	void deleteEmployeeByPin(String pin);
	String findAllEmployees();
	boolean isEmployeePinUnique(Integer id, String pin);
	Map getAllSetupEntity();
	Map<String,List<SetupEntity>>  getEmployeeInfo(int i);
	String update(Employee employee);
	ResultSet getSqlServerEmployee(String pin);
	void prepareNewEmployeeFromPaySlip(Object object);
	String getUserById(int i);
}