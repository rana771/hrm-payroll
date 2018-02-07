package com.bracu.hrm.service;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by HP on 1/24/2018.
 */
public interface EmployeeEducationService {
    String save(MultipartFile multipartFile, WebRequest webRequest);
    String getEmpEducationList(int i);
    String getEmpEduById(int i);
    String update(MultipartFile multipartFile, WebRequest webRequest);
    String delete(int i);
}
