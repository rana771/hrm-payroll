package com.bracu.hrm.service;

import com.bracu.hrm.dao.*;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.model.EmployeeContact;
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
 * Created by HP on 2/4/2018.
 */
@Service
@Transactional
public class EmployeeContactServiceImpl implements EmployeeContactService {
    @Autowired
    private EmployeeContactDao employeeContactDao;
    @Autowired
    private EntityTypeDao entityTypeDao;
    @Autowired
    private SetupEntityDao setupEntityDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    MessageSource messageSource;

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public Map getContactByEmpId(int empId) {
        HashedMap listMap =new HashedMap();
        Employee employee=employeeDao.findById(empId);
        listMap.put("employee",employee);
        EntityType entityTypeAddress =entityTypeDao.findByName("Contact Type");
        List<SetupEntity> contactTypeList= setupEntityDao.findAllByEntityType(entityTypeAddress);
        listMap.put("contactTypeList", contactTypeList);
        System.err.println(listMap);
        return listMap;
    }

    @Override
    @Transactional
    public String save(EmployeeContact employeeContact) {
        employeeContact.setVersion(0);
        employeeContact.setDateCreate(new Date());
        SetupEntity setupEntity=setupEntityDao.findById(employeeContact.getId());
        employeeContact.setContactType(setupEntity);
        employeeContactDao.save(employeeContact);
        String message = messageSource.getMessage("save.successful.message", new String[]{"Employee contact", employeeContact.getContactType().getColumn1()}, Locale.getDefault());
        return message;
    }

    /* @Override
    @Transactional
    public String getEmpAddressList(int id) {
        List<EmployeeAddress> employeeAddressList=employeeAddressDao.getList(id);
        return JSONUtil.getJsonObject(employeeAddressList);
    }
    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getEmpAddressById(int id) {
        List<EmployeeAddress> employeeAddressList=employeeAddressDao.findEmpAddressByAddressId(id);
        return JSONUtil.getJsonObject(employeeAddressList.get(0));
    }

    @Override
    @Transactional
    public String update(EmployeeAddress employeeAddress) {

        EmployeeAddress currentEmpAddress=employeeAddressDao.getEmployeeAddressById(employeeAddress.getId());
        if(currentEmpAddress.getVersion()>employeeAddress.getVersion()){
            String message = messageSource.getMessage("update.version.change", new String[]{"Employee Address Information", String.valueOf(employeeAddress.getVersion())}, Locale.getDefault());
            return message;
        }else{
            currentEmpAddress.setDateLastUpdate(new Date());
            currentEmpAddress.setAddress(employeeAddress.getAddress());
            Employee employee= employeeDao.findById(employeeAddress.getEmployee().getId());
            currentEmpAddress.setEmployee(employee);
            SetupEntity setupEntity=setupEntityDao.findById(employeeAddress.getAddressType().getId());
            currentEmpAddress.setAddressType(setupEntity);
            currentEmpAddress.setIsActive(employeeAddress.getIsActive());
            if(employeeAddress.getVersion()!=null)
                currentEmpAddress.setVersion(employeeAddress.getVersion()+1);
            employeeAddressDao.save(currentEmpAddress);
            String message = messageSource.getMessage("save.updated.message", new String[]{"Employee Address Information", employeeAddress.getAddress()}, Locale.getDefault());
            return message;
        }

    }

    @Override
    @Transactional
    public String delete(EmployeeAddress employeeAddress) {
        EmployeeAddress currentEmployeeAddress=employeeAddressDao.getEmployeeAddressById(employeeAddress.getId());
        if(!currentEmployeeAddress.getVersion().equals(employeeAddress.getVersion())||employeeAddress==null){
            String message = messageSource.getMessage("delete.version.message", new String[]{"Employee Address Information", String.valueOf(currentEmployeeAddress.getVersion())}, Locale.getDefault());
            return message;
        }else{
            employeeAddressDao.delete(currentEmployeeAddress);
            String message = messageSource.getMessage("delete.success.message", new String[]{"Employee Address Information", String.valueOf(currentEmployeeAddress.getVersion())}, Locale.getDefault());
            return message;

        }
    }*/
}
