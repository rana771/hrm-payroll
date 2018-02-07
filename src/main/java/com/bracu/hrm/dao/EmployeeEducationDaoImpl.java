package com.bracu.hrm.dao;

import com.bracu.hrm.model.EmployeeEducation;
import com.bracu.hrm.model.leave.LeaveType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by HP on 1/24/2018.
 */
@Repository("employeeEducationDao")
@Transactional
public class EmployeeEducationDaoImpl extends AbstractDao<Integer, EmployeeEducation> implements EmployeeEducationDao{
    static final Logger logger = LoggerFactory.getLogger(EmployeeEducationDaoImpl.class);
    @Override
    public void save(EmployeeEducation employeeEducation) {
        persist(employeeEducation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EmployeeEducation> getEmpEduList(int id) {
        String sql="SELECT \n"+
                    "empedu.id AS id ,\n"+
                    "empedu.board AS board ,\n"+
                    "empedu.institute AS institute,\n"+
                    "empedu.result AS result,\n"+
                    "empedu.passing_year AS passing_year,\n"+
                    "emp.id AS employee_Id,\n"+
                    "emp.full_name AS full_Name,\n"+
                    "emptitle.column1 AS title \n"+
                    "FROM \n"+
                    "employee_education AS empedu,\n"+
                    "employee AS emp,\n"+
                    "setup_entity As emptitle \n"+
                    "WHERE\n"+
                    "empedu.empoyee_id= emp.id\n"+
                    "AND empedu.education_title_id = emptitle.id\n"+
                    "AND empedu.empoyee_id="+id;



        return  executeSQL(sql);
    }

    @Override
    public List<EmployeeEducation> getEmpEduById(int id) {
        String sql= "SELECT DISTINCT \n"+
                "id, \n" +
                "board, \n" +
                "institute, \n" +
                "result, \n" +
                "passing_year, \n" +
                "empoyee_id, \n" +
                "education_title_id \n" +
                "FROM \n" +
                "employee_education \n" +
                "WHERE id="+id;

        return executeSQL(sql);
    }
    @Override
    public EmployeeEducation getEmpEduObject(int id){
        Criteria criteria = createEntityCriteria();//.addOrder(Order.asc("column1"));
        criteria.add(Restrictions.eq("id", id));
        EmployeeEducation employeeEducation = (EmployeeEducation) criteria.uniqueResult();
        return employeeEducation;
    }


}
