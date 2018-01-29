package com.bracu.hrm.dao;

import com.bracu.hrm.model.EmployeeAddress;

import java.util.List;

/**
 * Created by HP on 1/28/2018.
 */
public interface EmployeeAddressDao  {
    List<EmployeeAddress> findEmpAddressById(int employeeId);
    void save(EmployeeAddress employeeAddress);
    List<EmployeeAddress> getList(int id);
    List<EmployeeAddress> findEmpAddressByAddressId(int id);
    EmployeeAddress getEmployeeAddressById(int id);
    void delete(EmployeeAddress employeeAddress);
}
