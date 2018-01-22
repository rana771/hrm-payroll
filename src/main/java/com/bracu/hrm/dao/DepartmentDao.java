package com.bracu.hrm.dao;

import com.bracu.hrm.model.org.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DepartmentDao {

    void save(Department department);
    List<Department> findAll();
    boolean uniqueNameTest(Integer id, String name);

    Department findById(int id);

    void delete(Integer id);
}
