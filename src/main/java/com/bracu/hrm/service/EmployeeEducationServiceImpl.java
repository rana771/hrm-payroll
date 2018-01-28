package com.bracu.hrm.service;

import com.bracu.hrm.dao.EmployeeEducationDao;
import com.bracu.hrm.dao.SetupEntityDao;
import com.bracu.hrm.dbconfig.ReadOnlyConnection;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.EmployeeEducation;
import com.bracu.hrm.model.settings.SetupEntity;
import com.bracu.hrm.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by HP on 1/24/2018.
 */
@Service
public class EmployeeEducationServiceImpl implements EmployeeEducationService {
    @Autowired
    private EmployeeEducationDao employeeEducationDao;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SetupEntityDao setupEntityDao;
    @Autowired
    private MessageSource messageSource;
    @Override
    @Transactional
    public String save(MultipartFile multipartFile, WebRequest webRequest) {
        EmployeeEducation employeeEducation=new EmployeeEducation();
        String message;
        employeeEducation.setDateCreate(new Date());
        employeeEducation.setVersion(0);
        employeeEducation.setBoard(webRequest.getParameter("board"));
        employeeEducation.setInstitute(webRequest.getParameter("institute"));
        employeeEducation.setPassingYear(Integer.parseInt(webRequest.getParameter("passingYear")));
        employeeEducation.setResult(webRequest.getParameter("result"));
        Employee employee= employeeService.findById(Integer.parseInt(webRequest.getParameter("employeeId")));
        employeeEducation.setEmployee(employee);
        SetupEntity setupEntity= setupEntityDao.findById(Integer.parseInt(webRequest.getParameter("educationTitleId")));
        employeeEducation.setEducationTitle(setupEntity);
        try {
            employeeEducation.setCertificate(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        employeeEducationDao.save(employeeEducation);
        message = messageSource.getMessage("save.successful.message", new String[]{"Employee Education", employeeEducation.getEmployee().getFullName()}, Locale.getDefault());
        return message;
    }

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getEmpEducationList(int id) {
        List<EmployeeEducation> employeeEducationList=employeeEducationDao.getEmpEduList(id);
        String jesonObject=JSONUtil.getJsonObject(employeeEducationList);
        System.err.println(jesonObject);
        return JSONUtil.getJsonObject(employeeEducationList);
    }

    @Override
    @ReadOnlyConnection
    @Transactional(readOnly = true)
    public String getEmpEduById(int id) {
        List<EmployeeEducation> employeeEducation=employeeEducationDao.getEmpEduById(id);
        return JSONUtil.getJsonObject(employeeEducation.get(0));
    }
    @Override
    @Transactional
    public String update(MultipartFile multipartFile, WebRequest webRequest) {
        int id = Integer.parseInt(webRequest.getParameter("id"));
        EmployeeEducation employeeEducation = employeeEducationDao.getEmpEduObject(id);
        System.err.println(employeeEducation.getBoard());
       /* if (Integer.parseInt(webRequest.getParameter("version")) < employeeEducation.getVersion()) {
            String message = messageSource.getMessage("update.version.change", new String[]{"Employee Education", String.valueOf(employeeEducation.getVersion())}, Locale.getDefault());
            return message;
        } else {*/
            employeeEducation.setDateLastUpdate(new Date());
            employeeEducation.setBoard(webRequest.getParameter("board"));
            employeeEducation.setInstitute(webRequest.getParameter("institute"));
            employeeEducation.setPassingYear(Integer.parseInt(webRequest.getParameter("passingYear")));
            employeeEducation.setResult(webRequest.getParameter("result"));
            Employee employee = employeeService.findById(Integer.parseInt(webRequest.getParameter("employeeId")));
            employeeEducation.setEmployee(employee);
            SetupEntity setupEntity = setupEntityDao.findById(Integer.parseInt(webRequest.getParameter("educationTitleId")));
            employeeEducation.setEducationTitle(setupEntity);
            if (!multipartFile.isEmpty()) {
                try {
                    employeeEducation.setCertificate(multipartFile.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            employeeEducationDao.save(employeeEducation);
            String message = messageSource.getMessage("save.updated.message", new String[]{"Employee Education", employeeEducation.getEmployee().getFullName()}, Locale.getDefault());
            return message;
            //}

        //}
    }

    @Override
    @Transactional
    public String delete(int id) {
        EmployeeEducation employeeEducation = employeeEducationDao.getEmpEduObject(id);
        if(employeeEducation ==null){
            String message = messageSource.getMessage("delete.version.message", new String[]{"Employee Education", ""}, Locale.getDefault());
            return message;
        }
        employeeEducationDao.delete(employeeEducation);
        String message = messageSource.getMessage("delete.success.message", new String[]{"Employee Education", String.valueOf(employeeEducation.getVersion())}, Locale.getDefault());
        return message ;
    }

}
