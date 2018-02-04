package com.bracu.hrm.dao;

import com.bracu.hrm.model.EmployeeDesignation;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HP on 2/1/2018.
 */
@Repository("employeeDesignation")
@Transactional
public class EmployeeDesignationDaoImpl extends AbstractDao<Integer, EmployeeDesignation> implements EmployeeDesignaitonDao {
    @Override
    public List<EmployeeDesignation> getEmpDesignationByEmpId(int empId) {
        String sql=
                "SELECT\n"+
                        "empdes.id,\n" +
                        "empdes.start_Date,\n"+
                        "empdes.end_Date,\n"+
                        "empdes.is_active,\n"+
                        "des.name\n"+
                        "FROM \n"+
                        "employee_designation as empdes\n"+
                        "INNER JOIN \n"+
                        "designation as des\n"+
                        "ON\n"+
                        "empdes.designation_id=des.id\n"+
                        "WHERE employee_id="+empId;
       /* Criteria criteria = createEntityCriteria();//.addOrder(Order.asc("column1"));
        List<EmployeeDesignation> employeeDesignationList = (List<EmployeeDesignation>) criteria.list();
        return employeeDesignationList;*/
        return executeSQL(sql);
    }

    @Override
    public void save(EmployeeDesignation employeeDesignation) {
        persist(employeeDesignation);

    }

    @Override
    public List<EmployeeDesignation> getEmpDesignationById(int id) {
        String sql=
                "SELECT * FROM employee_designation WHERE id="+id;
        return executeSQL(sql);

    }
    @Override
    public EmployeeDesignation getEmpDesignationEntityById(Integer id) {
        Criteria criteria = createEntityCriteria();//.addOrder(Order.asc("column1"));
        criteria.add(Restrictions.eq("id",id));
        return (EmployeeDesignation) criteria.uniqueResult();
    }

}
