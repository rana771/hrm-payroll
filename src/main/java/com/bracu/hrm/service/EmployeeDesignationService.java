package com.bracu.hrm.service;

import com.bracu.hrm.model.EmployeeDesignation;

import java.util.Map;

/**
 * Created by HP on 2/1/2018.
 */
public interface EmployeeDesignationService {
    Map getEmpDesinationInfo(int id);
    String getEmpDesignationListByEmpId(int empId);
    String save(EmployeeDesignation employeeDesignation);
    String getEmpDesignationById(int i);
    String update(EmployeeDesignation employeeDesignation);
    String delete(EmployeeDesignation employeeDesignation);
}
