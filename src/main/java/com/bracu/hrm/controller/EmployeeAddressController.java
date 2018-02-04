package com.bracu.hrm.controller;

import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.service.EmployeeAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by HP on 1/28/2018.
 */
@Controller
public class EmployeeAddressController {
    @Autowired
    private EmployeeAddressService employeeAddressService;
    @RequestMapping(value = "/employee/address/{id}", method = RequestMethod.GET)
    public String createEmpAddress(@PathVariable("id") String id, ModelMap model){
        Map setupList =  employeeAddressService.getEmpAddressInfo(Integer.parseInt(id));
        model.addAttribute("employee",setupList.get("employee"));
        model.addAttribute("addressType",setupList.get("addressType"));
        return "employee/address/address";
    }

    @ResponseBody
    @RequestMapping(value = { "/employee/address/save" }, method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
    public String save(@RequestBody EmployeeAddress employeeAddress, BindingResult result) {
        if (result.hasErrors()) {
            return "employee/address/address";
        } else{
            return employeeAddressService.save(employeeAddress);
        }

    }
    @ResponseBody
    @RequestMapping(value = "/address/list/", method = RequestMethod.POST)
    public String getEmpAddressList(@RequestParam("id") String id){
        String list = employeeAddressService.getEmpAddressList(Integer.parseInt(id));
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/address/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") String id, ModelMap model){
        String empAddressInfo = employeeAddressService.getEmpAddressById(Integer.parseInt(id));
        return empAddressInfo ;
    }
    @ResponseBody
    @RequestMapping(value = { "/employee/address/update" }, method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
    public String update(@RequestBody EmployeeAddress employeeAddress, BindingResult resultItem) {
        if (resultItem.hasErrors()) {
            return "employee/address/address";
        } else{
            return employeeAddressService.update(employeeAddress);
        }

    }
    @ResponseBody
    @RequestMapping(value = { "/employee/address/delete" }, method = RequestMethod.POST)
    public String delete(@RequestBody EmployeeAddress employeeAddress, BindingResult resultItem) {
        if (resultItem.hasErrors()) {
            return "employee/address/address";
        } else{
            return employeeAddressService.delete(employeeAddress);
        }

    }
}
