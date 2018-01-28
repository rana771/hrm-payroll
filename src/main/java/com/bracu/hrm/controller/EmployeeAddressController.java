package com.bracu.hrm.controller;

import com.bracu.hrm.service.EmployeeAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by HP on 1/28/2018.
 */
@Controller
public class EmployeeAddressController {
    @Autowired
    private EmployeeAddressService employeeAddressService;
    @RequestMapping(value = "/education/{id}", method = RequestMethod.GET)
    public String CreateEmpAddress(@PathVariable("id") String id, ModelMap model){
        Map setupList =  employeeAddressService.getEmpAddressInfo(Integer.parseInt(id));
        model.addAttribute("addressType",setupList.get("addressTypeList"));
        model.addAttribute("employeeAddress",setupList.get("employeeAddress"));
        return "employee/address/address";
    }
}
