package com.bracu.hrm.service;

import com.bracu.hrm.model.org.Department;
import java.security.Principal;

public interface DepartmentService {
    void save(Department leaveTypdepartment, Principal principal);
    void save(Department department);
    String getDepartmentList();
    boolean uniqueNameCheck(Integer id, String name);

    String getDepartmentById(int i);

    String update(Department department);

    String delete(int i);
}
