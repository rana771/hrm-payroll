package com.bracu.hrm.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bracu.hrm.model.org.Department;

import java.util.List;

@Repository("departmentDao")
public class DepartmentDaoImpl extends AbstractDao<Integer, Department> implements  DepartmentDao{

    public void save(Department department) {
        super.persist(department);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Department> findAll() {
        Criteria criteria = createEntityCriteria();//.addOrder(Order.asc("column1"));
        List<Department> DepartmentList = (List<Department>) criteria.list();
        return DepartmentList;
    }

    @Override
    public Department findById(Integer id) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("id",id));
        Department department = (Department) criteria.uniqueResult();
        return  department;
    }



    @Override
    public void delete(Integer id) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("id", id));
        Department department= (Department) crit.uniqueResult();
        delete(department);
    }



}
