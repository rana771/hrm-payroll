package com.bracu.hrm.dao;

import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.model.EmployeeDesignation;

import java.util.List;

/**
 * Created by HP on 2/1/2018.
 */
public interface EmployeeDesignaitonDao {
    List<EmployeeDesignation> getEmpDesignationByEmpId(int empId);
    void save(EmployeeDesignation employeeDesignation);
    List<EmployeeDesignation> getEmpDesignationById(int id);
    EmployeeDesignation getEmpDesignationEntityById(Integer id);
    void delete(EmployeeDesignation currentEmployeeDesignation);
}
