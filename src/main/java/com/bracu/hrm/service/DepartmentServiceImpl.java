package com.bracu.hrm.service;

import com.bracu.hrm.dao.DepartmentDao;
import com.bracu.hrm.model.org.Department;
import com.bracu.hrm.util.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    MessageSource messageSource;

    @Override
    public void save(Department department){
        department.setVersion(0);
//        department.setDateCreated(new Date());
//        department.setDateLastUpdated(new Date());
    departmentDao.save(department);
    }

    @Override
    public String getDepartmentList() {
        List<Department> list = departmentDao.findAll();
        return JSONUtil.getJsonObject(list);
    }

    @Override
    public boolean uniqueNameCheck(Integer id, String name) {
        return departmentDao.uniqueNameTest(id,name);
    }

    @Override
    public String getDepartmentById(int i) {
        Department department = departmentDao.findById(i);
        return JSONUtil.getJsonObject(department);
    }

    @Override
    public String update(Department department) {
        Department currentDept = departmentDao.findById(department.getId());
        if (department.getVersion() < currentDept.getVersion()){
            String message=messageSource.getMessage("update.version.change", new String[]{"Department Information", String.valueOf(department.getVersion())}, Locale.getDefault());
            return message;
        } else {
            currentDept.setName(department.getName());
            currentDept.setShortName(department.getShortName());
            currentDept.setDateLastUpdate(new Date());
            currentDept.setVersion(department.getVersion()+1);
            departmentDao.save(currentDept);
//            department.getId();
            String message = messageSource.getMessage("save.updated.message", new String[]{"Department Information", department.getName()}, Locale.getDefault());
            return message;
        }
    }



    @Override
    public String delete(int id) {
        Department leaveType= departmentDao.findById(id);
        String resultJson = "";
        try {
            departmentDao.delete(leaveType.getId());
            Gson gson = new Gson();
            String message = messageSource.getMessage("delete.success.message", new String[]{"Department Information", leaveType.getName()}, Locale.getDefault());
            resultJson = gson.toJson(message);


        }catch (JsonIOException e){
            System.out.println(e.getMessage());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultJson;
    }

    @Override
    public void save(Department department, Principal principal) {
        department.setDateCreate(new Date());
        department.setVersion(0);
        departmentDao.save(department);

    }



}
