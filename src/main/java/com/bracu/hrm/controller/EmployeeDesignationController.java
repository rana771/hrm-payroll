package com.bracu.hrm.controller;

import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.model.EmployeeDesignation;
import com.bracu.hrm.service.EmployeeAddressService;
import com.bracu.hrm.service.EmployeeDesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by HP on 2/1/2018.
 */
@Controller
public class EmployeeDesignationController {
    @Autowired
    private EmployeeDesignationService designationService;
    @RequestMapping(value = "/emp/designation/{id}", method = RequestMethod.GET)
    public String createEmpDesignation(@PathVariable("id") String id, ModelMap model){
        Map setupList =  designationService.getEmpDesinationInfo(Integer.parseInt(id));
        model.addAttribute("employee",setupList.get("employee"));
        model.addAttribute("designationList",setupList.get("designationList"));
        return "employee/designation/designation";
    }

    @ResponseBody
    @RequestMapping(value = { "/designation/save" }, method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
    public String save(@RequestBody EmployeeDesignation employeeDesignation, BindingResult result) {
        if (result.hasErrors()) {
            return "employee/designation/designation";
        } else{
            return designationService.save(employeeDesignation);
        }


    }
    @ResponseBody
    @RequestMapping(value = "/designation/list/", method = RequestMethod.POST)
    public String getEmpDesignationList(@RequestParam("id") String id){
        String list = designationService.getEmpDesignationListByEmpId(Integer.parseInt(id));
        System.err.println(list);
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/designation/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") String id, ModelMap model){
        String employeeDesignation = designationService.getEmpDesignationById(Integer.parseInt(id));
        return employeeDesignation ;
    }
    @ResponseBody
    @RequestMapping(value = { "/designation/update" }, method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
    public String update(@RequestBody EmployeeDesignation employeeDesignation, BindingResult resultItem) {
        if (resultItem.hasErrors()) {
            return "employee/designation/designation";
        } else{
            return designationService.update(employeeDesignation);
        }

    }
    @ResponseBody
    @RequestMapping(value = { "/designation/delete/" }, method = RequestMethod.POST)
    public String delete(@RequestBody EmployeeDesignation employeeDesignation, BindingResult resultItem) {
        if (resultItem.hasErrors()) {
            return "employee/designation/designation";
        } else{
            return designationService.delete(employeeDesignation);
        }

    }
}
