package com.bracu.hrm.dao;

import com.bracu.hrm.model.Employee;
import com.bracu.hrm.util.SQLDataSource;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public ResultSet getSqlServerEmployee(String pin){
		Connection con  = new SQLDataSource().getSqlConnection();
		ResultSet rs = null;
		try
		{
			con = new SQLDataSource().getSqlConnection();
			Statement s1 = con.createStatement();
			String sqlString = "select * from hr_employee_t WHERE hr_employee_t.pin = '"+ pin +"'";
			rs = s1.executeQuery(sqlString);

			//allResult = Utility.convertResultSetToList(rs);
			//con.close();
			//String result = new result[20];

		}catch (SQLException e) {


		}catch
				(Exception e)
		{
			e.printStackTrace();
		}


		return rs;
	}

	@Override
	public List<Employee> findUserByEmployeeId(Integer id) {
		String sql = "SELECT \n" +
				"employee.id,\n"+
				"app_user.id,\n"+
				"app_user.username,\n"+
				"app_user.email\n"+
				"FROM employee ,app_user\n"+
				"WHERE employee.id  = id \n";

		return  executeSQL(sql);


	}

}
