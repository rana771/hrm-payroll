package com.bracu.hrm.dao;

import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.EmployeeAddress;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HP on 1/28/2018.
 */
@Repository("employeeAddressDao")
@Transactional
public class EmployeeAddressDaoImpl extends AbstractDao<Integer, EmployeeAddress> implements EmployeeAddressDao {
    static final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Override
    @SuppressWarnings("unchecked")
    public List<EmployeeAddress> findEmpAddressById(int employeeId) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("employee.id", employeeId));
        return crit.list();
    }

    @Override
    public void save(EmployeeAddress employeeAddress) {
        persist(employeeAddress);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EmployeeAddress> getList(int id) {
        String sql="SELECT \n"+
                "empadd.id AS id ,\n"+
                "empadd.address AS address ,\n"+
                "empadd.is_Active AS status,\n"+
                "emp.id AS employee_Id,\n"+
                "empaddType.column1 AS addressType\n"+
                "FROM \n"+
                "employee_address AS empadd,\n"+
                "employee AS emp,\n"+
                "setup_entity As empaddType\n"+
                "WHERE \n"+
                "empadd.empoyee_id= emp.id\n"+
                "AND empadd.address_type_id= empaddType.id\n"+
                "AND empadd.empoyee_id="+id;
        return  executeSQL(sql);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EmployeeAddress> findEmpAddressByAddressId(int id) {
        String sql="SELECT \n"+
                "id ,\n"+
                "address ,\n"+
                "is_Active,\n"+
                "empoyee_id,\n"+
                "address_type_id,\n"+
                "version\n"+
                "FROM \n"+
                "employee_address\n"+
                "WHERE \n"+
                "id="+id;
        /* Criteria criteria = createEntityCriteria();//.addOrder(Order.asc("column1"));
        criteria.add(Restrictions.eq("id", id));
        EmployeeAddress employeeAddress = (EmployeeAddress) criteria.uniqueResult();*/
        return executeSQL(sql);
    }

    @Override
    public EmployeeAddress getEmployeeAddressById(int id) {
        Criteria criteria = createEntityCriteria();//.addOrder(Order.asc("column1"));
        criteria.add(Restrictions.eq("id", id));
        EmployeeAddress employeeAddress = (EmployeeAddress) criteria.uniqueResult();
        return employeeAddress;
    }

}
