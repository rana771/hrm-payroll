package com.bracu.hrm.service;

import com.bracu.hrm.dao.EmployeeAddressDao;
import com.bracu.hrm.dao.EmployeeDao;
import com.bracu.hrm.dao.EntityTypeDao;
import com.bracu.hrm.dao.SetupEntityDao;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.dto.EmpAddressDto;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.model.settings.EntityType;
import com.bracu.hrm.model.settings.SetupEntity;
import com.bracu.hrm.util.JSONUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by HP on 1/28/2018.
 */
@Service
public class EmployeeAddressServiceImpl implements EmployeeAddressService {
    @Autowired
    private EmployeeAddressDao employeeAddressDao;
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
    public Map getEmpAddressInfo(int id) {
        HashedMap listMap =new HashedMap();
        Employee employee=employeeDao.findById(id);
        listMap.put("employee",employee);
        List<EmployeeAddress> employeeAddressList=employeeAddressDao.findEmpAddressById(id);
        listMap.put("employeeAddressList",employeeAddressList);
        EntityType entityTypeAddress =entityTypeDao.findByName("Address");
        List<SetupEntity> addressTypeList= setupEntityDao.findAllByEntityType(entityTypeAddress);
        listMap.put("addressType", addressTypeList);
        System.err.println(listMap);
        return listMap;
    }

    @Override
    @Transactional
    public String save(EmpAddressDto empAddressDto) {
        EmployeeAddress employeeAddress=new EmployeeAddress();
        employeeAddress.setVersion(0);
        employeeAddress.setDateCreate(new Date());
        employeeAddress.setAddress(empAddressDto.getAddress());
        Employee employee= employeeDao.findById(empAddressDto.getEmployeeId());
        employeeAddress.setEmployee(employee);
        SetupEntity setupEntity=setupEntityDao.findById(empAddressDto.getAddressTypeId());
        employeeAddress.setAddressType(setupEntity);
        employeeAddress.setIsActive(empAddressDto.getIsActive().equals("true")?true:false);
        employeeAddressDao.save(employeeAddress);
        String message = messageSource.getMessage("save.successful.message", new String[]{"Employee Address Information", employeeAddress.getAddress()}, Locale.getDefault());
        return message;
    }

    @Override
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
    public String update(EmpAddressDto addressDto) {

        EmployeeAddress employeeAddress=employeeAddressDao.getEmployeeAddressById(addressDto.getId());
        if(employeeAddress.getVersion()>addressDto.getVersion()){
            String message = messageSource.getMessage("update.version.change", new String[]{"Employee Address Information", String.valueOf(addressDto.getVersion())}, Locale.getDefault());
            return message;
        }else{
            employeeAddress.setDateLastUpdate(new Date());
            employeeAddress.setAddress(addressDto.getAddress());
            Employee employee= employeeDao.findById(addressDto.getEmployeeId());
            employeeAddress.setEmployee(employee);
            SetupEntity setupEntity=setupEntityDao.findById(addressDto.getAddressTypeId());
            employeeAddress.setAddressType(setupEntity);
            employeeAddress.setIsActive(addressDto.getIsActive().equals("true")?true:false);
            employeeAddress.setVersion(addressDto.getVersion()+1);
            employeeAddressDao.save(employeeAddress);
            String message = messageSource.getMessage("save.updated.message", new String[]{"Employee Address Information", employeeAddress.getAddress()}, Locale.getDefault());
            return message;
        }

    }

    @Override
    @Transactional
    public String delete(EmpAddressDto empAddressDto) {
        EmployeeAddress employeeAddress=employeeAddressDao.getEmployeeAddressById(empAddressDto.getId());
        if(!empAddressDto.getVersion().equals(employeeAddress.getVersion())||employeeAddress==null){
            String message = messageSource.getMessage("delete.version.message", new String[]{"Employee Address Information", String.valueOf(employeeAddress.getVersion())}, Locale.getDefault());
            return message;
        }else{
            employeeAddressDao.delete(employeeAddress);
            String message = messageSource.getMessage("delete.success.message", new String[]{"Employee Address Information", String.valueOf(employeeAddress.getVersion())}, Locale.getDefault());
            return message;

        }
    }


}
