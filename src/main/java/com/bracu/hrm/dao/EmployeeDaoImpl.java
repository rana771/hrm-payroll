package com.bracu.hrm.dao;

import com.bracu.hrm.model.Employee;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository("employeeDao")
public class EmployeeDaoImpl extends AbstractDao<Integer, Employee> implements EmployeeDao {

	static final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);
	
	public Employee findById(int id) {
		Employee employee = getByKey(id);
		/*if(employee!=null){
			Hibernate.initialize(employee.getUserProfiles());
		}*/
		return employee;
	}

	public Employee findByPin(String pin) {
		logger.info("PIN : {}", pin);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("pin", pin));
		Employee employee = (Employee)crit.uniqueResult();
		/*if(user!=null){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		return employee;
	}

	@SuppressWarnings("unchecked")
	public List<Employee> findAllEmployees() {
		/*Criteria criteria = createEntityCriteria().addOrder(Order.asc("pin"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<Employee> employeeList = (List<Employee>) criteria.list();*/
		
		// No need to fetch userProfiles since we are not showing them on list page. Let them lazy load. 
		// Uncomment below lines for eagerly fetching of userProfiles if you want.
		/*
		for(User user : users){
			Hibernate.initialize(user.getUserProfiles());
		}*/
		String sql = "SELECT \n" +
				"id AS id,\n" +
				"pin AS Pin,\n"+
				"Full_Name AS name, \n" +
				"email  AS email, \n" +
				"father_name AS fname, \n" +
				"date_joining AS doj, \n" +
				"date_confirmation AS doc, \n" +
				"nid AS nid \n" +
				"FROM employee \n" +
				"OFFSET 0 \n"+
				"LIMIT 100 \n";
				return  executeSQL(sql);
	}

	public void save(Employee employee) {
		persist(employee);
	}

	public void deleteByPin(String pin) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("pin", pin));
		Employee employee = (Employee)crit.uniqueResult();
		delete(employee);
	}

}
