package com.bracu.hrm.dao;
import com.bracu.hrm.exception.DatabaseException;
import com.bracu.hrm.model.EmployeeContact;
import com.bracu.hrm.model.EmployeeDesignation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Created by HP on 2/4/2018.
 */
@Repository("employeeContactDao")
@Slf4j
public class EmployeeContactDaoImpl extends AbstractDao<Integer, EmployeeContact> implements EmployeeContactDao {
    @Override
    public void save(EmployeeContact employeeContact) {
        persist(employeeContact);
    }
}
