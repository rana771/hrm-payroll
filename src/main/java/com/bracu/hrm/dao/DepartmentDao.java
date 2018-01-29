package com.bracu.hrm.dao;

import com.bracu.hrm.model.org.Department;
import java.util.List;

public interface DepartmentDao {

    void save(Department department);
    List<Department> findAll();
    boolean uniqueNameTest(Integer id, String name);

    void delete(Integer id);

	Department findById(Integer id);
}

