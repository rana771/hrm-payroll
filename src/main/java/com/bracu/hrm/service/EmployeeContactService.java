package com.bracu.hrm.service;

import com.bracu.hrm.model.EmployeeContact;

import java.util.Map;

/**
 * Created by HP on 2/4/2018.
 */
public interface EmployeeContactService {
    Map getContactByEmpId(int empId);
    String save(EmployeeContact employeeContact);
}
