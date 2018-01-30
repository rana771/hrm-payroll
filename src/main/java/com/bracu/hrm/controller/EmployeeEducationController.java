package com.bracu.hrm.controller;

import com.bracu.hrm.model.EmployeeEducation;
import com.bracu.hrm.service.EmployeeEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.QueryParam;
import java.io.IOException;

/**
 * Created by HP on 1/24/2018.
 */
@Controller
public class EmployeeEducationController {
    @Autowired
    private EmployeeEducationService employeeEducationService;
    @ResponseBody
    @RequestMapping(value = { "/education/save" }, method = RequestMethod.POST)
    public String saveEmpEducation(@RequestParam("file") MultipartFile multipartFile, WebRequest webRequest) throws IOException {
        String message=employeeEducationService.save(multipartFile,webRequest);
        return message;

    }
    @ResponseBody
    @RequestMapping(value = "/education/list", method = RequestMethod.POST)
    public String getEmpEducationList(@RequestParam("id") String id){
        String list = employeeEducationService.getEmpEducationList(Integer.parseInt(id));
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/education/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") String id, ModelMap model){
        //System.err.println(id);
        String empEducationInfo = employeeEducationService.getEmpEduById(Integer.parseInt(id));
        //System.err.println(empEducationInfo);
        return empEducationInfo ;
    }
    @ResponseBody
    @RequestMapping(value = { "/education/update" }, method = RequestMethod.POST)
    public String updateEmpEducation(@RequestParam(name = "file") MultipartFile multipartFile, WebRequest webRequest) throws IOException {
      System.err.println(multipartFile.getOriginalFilename());
        String message=employeeEducationService.update(multipartFile,webRequest);
        return message;

    }
    @ResponseBody
    @RequestMapping(value = "/education/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") String id, ModelMap model){
        String message = employeeEducationService.delete(Integer.parseInt(id));
        System.err.println(message);
        return message ;
    }


}
