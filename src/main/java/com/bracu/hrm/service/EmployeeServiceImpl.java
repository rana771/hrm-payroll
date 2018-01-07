package com.bracu.hrm.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bracu.hrm.dao.EmployeeDao;
import com.bracu.hrm.dao.EntityTypeDao;
import com.bracu.hrm.dao.SetupEntityDao;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.settings.EntityType;
import com.bracu.hrm.model.settings.SetupEntity;



@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private SetupEntityDao setupEntityDao;
	
	@Autowired
	private EntityTypeDao entityTypeDao;
	
	public Employee findById(int id) {
		return employeeDao.findById(id);
	}

	public Employee findByPin(String pin) {
		Employee employee = employeeDao.findByPin(pin);
		return employee;
	}

	public void saveEmployee(Employee employee) {
		employeeDao.save(employee);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateEmployee(Employee employee) {
		Employee entity = employeeDao.findById(employee.getId());
		if(entity!=null){
			entity.setPin(employee.getPin());

			entity.setFullName(employee.getFullName());
			entity.setEmail(employee.getEmail());
			entity.setDateOfBirith(employee.getDateOfBirith());
		}
	}

	
	public void deleteEmployeeByPin(String pin) {
		employeeDao.deleteByPin(pin);
	}

	public List<Employee> findAllEmployees() {
		return employeeDao.findAllEmployees();
	}

	public boolean isEmployeePinUnique(Integer id, String pin) {
		Employee employee = findByPin(pin);
		return ( employee == null || ((id != null) && (employee.getId() == id)));
	}
	
	
	public Map<String,List<SetupEntity>> getAllSetupEntity(){
		HashedMap listMap =new HashedMap();
		EntityType entityType =entityTypeDao.findByName("Gender");
		List <SetupEntity> genderList = setupEntityDao.findAllByEntityType(entityType);
		listMap.put("genderList", genderList);
		
		
		EntityType entityTypeMarritalStatus =entityTypeDao.findByName("Marrital Status");
		List <SetupEntity> marritalStatusList = setupEntityDao.findAllByEntityType(entityTypeMarritalStatus);
		listMap.put("marritalStatusList", marritalStatusList);
	/*	
		EntityType entityTypeCountry =entityTypeDao.findByName("Country");
		List <SetupEntity> countryList = setupEntityDao.findAllByEntityType(entityTypeCountry);
		listMap.put("countryList", countryList);
		*/
		EntityType entityTypeNationality =entityTypeDao.findByName("Nationality");
		List <SetupEntity> nationalityList = setupEntityDao.findAllByEntityType(entityTypeNationality);
		listMap.put("nationalityList", nationalityList);
		

		
		return listMap;
		
	}
}
