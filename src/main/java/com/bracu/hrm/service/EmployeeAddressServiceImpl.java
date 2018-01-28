package com.bracu.hrm.service;

import com.bracu.hrm.dao.EmployeeAddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by HP on 1/28/2018.
 */
@Service
public class EmployeeAddressServiceImpl implements EmployeeAddressService {
    @Autowired
    private EmployeeAddressDao employeeAddressDao;

    @Override
    public Map getEmpAddressInfo(int id) {

        return null;
    }
}
