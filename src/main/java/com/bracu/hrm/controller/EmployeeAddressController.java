package com.bracu.hrm.controller;

import com.bracu.hrm.dto.EmpAddressDto;
import com.bracu.hrm.model.EmployeeAddress;
import com.bracu.hrm.model.leave.LeaveType;
import com.bracu.hrm.service.EmployeeAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        System.err.println(id);
        Map setupList =  employeeAddressService.getEmpAddressInfo(Integer.parseInt(id));
        model.addAttribute("employee",setupList.get("employee"));
        model.addAttribute("addressType",setupList.get("addressType"));
        return "employee/address/address";
    }

    @ResponseBody
    @RequestMapping(value = { "/employee/address/save" }, method = RequestMethod.POST)
    public String save(@RequestBody EmpAddressDto empAddressDto,BindingResult result) {
        if (result.hasErrors()) {
            return "employee/address/address";
        } else{
            System.err.println(empAddressDto);

            return employeeAddressService.save(empAddressDto);
            //return "";
        }

    }
    @ResponseBody
    @RequestMapping(value = "/address/list/", method = RequestMethod.POST)
    public String getEmpAddressList(@RequestParam("id") String id){
        String list = employeeAddressService.getEmpAddressList(Integer.parseInt(id));
        System.err.println(list);
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/address/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") String id, ModelMap model){
        //System.err.println(id);
        String empAddressInfo = employeeAddressService.getEmpAddressById(Integer.parseInt(id));
        //System.err.println(empEducationInfo);
        return empAddressInfo ;
    }
    @ResponseBody
    @RequestMapping(value = { "/employee/address/update" }, method = RequestMethod.POST)
    public String update(@RequestBody EmpAddressDto empAddressDto, BindingResult resultItem) {
        if (resultItem.hasErrors()) {
            return "employee/address/address";
        } else{
            System.err.println(empAddressDto);
            return employeeAddressService.update(empAddressDto);
        }

    }
    @ResponseBody
    @RequestMapping(value = { "/employee/address/delete" }, method = RequestMethod.POST)
    public String delete(@RequestBody EmpAddressDto empAddressDto, BindingResult resultItem) {
        if (resultItem.hasErrors()) {
            return "employee/address/address";
        } else{
            System.err.println(empAddressDto);
            return employeeAddressService.delete(empAddressDto);
        }

    }
}
