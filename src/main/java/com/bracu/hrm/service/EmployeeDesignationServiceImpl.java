package com.bracu.hrm.service;

import com.bracu.hrm.dao.DesignationDao;
import com.bracu.hrm.dao.EmployeeDao;
import com.bracu.hrm.dao.EmployeeDesignaitonDao;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.model.EmployeeDesignation;
import com.bracu.hrm.model.org.Designation;
import com.bracu.hrm.model.settings.EntityType;
import com.bracu.hrm.model.settings.SetupEntity;
import com.bracu.hrm.util.JSONUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by HP on 2/1/2018.
 */
@Service
public class EmployeeDesignationServiceImpl implements EmployeeDesignationService{
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DesignationDao designationDao;
    @Autowired
    private EmployeeDesignaitonDao employeeDesignaitonDao;
    @Autowired
    private MessageSource messageSource;
    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public Map getEmpDesinationInfo(int id) {
        HashedMap listMap =new HashedMap();
        Employee employee=employeeDao.findById(id);
        listMap.put("employee",employee);
        List<Designation> designationList=designationDao.findAll();
        listMap.put("designationList",designationList);
        return listMap;

    }

    @Override
    @ReadOnlyConnection
    @Transactional
    public String getEmpDesignationListByEmpId(int empId) {
        List<EmployeeDesignation> employeeDesignationList=employeeDesignaitonDao.getEmpDesignationByEmpId(empId);
        return JSONUtil.getJsonObject(employeeDesignationList);
    }

    @Override
    @Transactional
    public String save(EmployeeDesignation employeeDesignation) {
        employeeDesignation.setVersion(0);
        employeeDesignation.setDateCreate(new Date());
        Designation designation=designationDao.findById(employeeDesignation.getDesignation().getId());
        employeeDesignation.setDesignation(designation);
        employeeDesignaitonDao.save(employeeDesignation);
        String message = messageSource.getMessage("save.successful.message", new String[]{"Employee Designation ", employeeDesignation.getDesignation().getName()}, Locale.getDefault());
        return message;

    }

    @Override
    @Transactional
    public String getEmpDesignationById(int id) {
        List<EmployeeDesignation> employeeDesignationList=employeeDesignaitonDao.getEmpDesignationById(id);
        return JSONUtil.getJsonObject(employeeDesignationList.get(0));
        //return employeeDesignation;
    }

    @Override
    @Transactional
    public String update(EmployeeDesignation employeeDesignation) {
        EmployeeDesignation currentEmpDesignation=employeeDesignaitonDao.getEmpDesignationEntityById(employeeDesignation.getId());
        if(currentEmpDesignation.getVersion()>employeeDesignation.getVersion()){
            String message = messageSource.getMessage("update.version.change", new String[]{"Designation", String.valueOf(employeeDesignation.getVersion())}, Locale.getDefault());
            return message;
        }else{
            currentEmpDesignation.setDateLastUpdate(new Date());
            Employee employee= employeeDao.findById(employeeDesignation.getEmployee().getId());
            currentEmpDesignation.setEmployee(employee);
            Designation designation=designationDao.findById(employeeDesignation.getDesignation().getId());
            currentEmpDesignation.setDesignation(designation);
            currentEmpDesignation.setIsActive(employeeDesignation.getIsActive());
            currentEmpDesignation.setStartDate(employeeDesignation.getStartDate());
            currentEmpDesignation.setEndDate(employeeDesignation.getEndDate());
            if(employeeDesignation.getVersion()!=null)
                currentEmpDesignation.setVersion(employeeDesignation.getVersion()+1);
            employeeDesignaitonDao.save(currentEmpDesignation);
            String message = messageSource.getMessage("save.updated.message", new String[]{"Employee Address Information", employeeDesignation.getDesignation().getName()}, Locale.getDefault());
            return message;
        }
    }
    @Override
    @Transactional
    public String delete(EmployeeDesignation employeeDesignation) {
        EmployeeDesignation currentEmployeeDesignation=employeeDesignaitonDao.getEmpDesignationEntityById(employeeDesignation.getId());
        if(!currentEmployeeDesignation.getVersion().equals(employeeDesignation.getVersion())||employeeDesignation==null){
            String message = messageSource.getMessage("delete.version.message", new String[]{"Employee Designation", String.valueOf(currentEmployeeDesignation.getVersion())}, Locale.getDefault());
            return message;
        }else{
            employeeDesignaitonDao.delete(currentEmployeeDesignation);
            String message = messageSource.getMessage("delete.success.message", new String[]{"Employee Designation", String.valueOf(currentEmployeeDesignation.getVersion())}, Locale.getDefault());
            return message;

        }
    }
}
