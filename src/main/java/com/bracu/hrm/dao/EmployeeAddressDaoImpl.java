package com.bracu.hrm.dao;

import com.bracu.hrm.model.EmployeeAddress;
import org.springframework.stereotype.Repository;

/**
 * Created by HP on 1/28/2018.
 */
@Repository("employeeAddress")
public class EmployeeAddressDaoImpl extends AbstractDao<Integer, EmployeeAddress> implements EmployeeAddressDao {
}
