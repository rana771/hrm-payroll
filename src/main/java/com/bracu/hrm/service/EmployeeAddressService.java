package com.bracu.hrm.service;

import com.bracu.hrm.dto.EmpAddressDto;
import com.bracu.hrm.model.EmployeeAddress;

import java.util.Map;

/**
 * Created by HP on 1/28/2018.
 */
public interface EmployeeAddressService {
    Map getEmpAddressInfo(int id);
    String save(EmployeeAddress employeeAddress);
    String getEmpAddressList(int id);
    String getEmpAddressById(int id);
    String update(EmployeeAddress employeeAddress);
    String delete(EmployeeAddress employeeAddress);
}
