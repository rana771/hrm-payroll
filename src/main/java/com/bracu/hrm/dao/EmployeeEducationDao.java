package com.bracu.hrm.dao;

import com.bracu.hrm.model.EmployeeEducation;

import java.util.List;

/**
 * Created by HP on 1/24/2018.
 */
public interface EmployeeEducationDao {
    void save(EmployeeEducation employeeEducation);
    List<EmployeeEducation> getEmpEduList(int id);
    List<EmployeeEducation> getEmpEduById(int id);
    EmployeeEducation getEmpEduObject(int id);
    void delete(EmployeeEducation employeeEducation);

}
