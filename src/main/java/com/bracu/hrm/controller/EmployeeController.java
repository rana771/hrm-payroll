package com.bracu.hrm.controller;

import com.bracu.hrm.model.Employee;
import com.bracu.hrm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	MessageSource messageSource;
	@RequestMapping(value = { "/employeeList", "/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		//List<Employee> employeeList = employeeService.findAllEmployees();
		//System.err.println(employeeList.size());
		//String list = employeeService.findAllEmployees();
		//model.addAttribute("employeeList", list);
		//model.addAttribute("loggedinuser", getPrincipal());
		return "employee/employeeList";
	}
	@ResponseBody
	@RequestMapping(value =  "/list2" , method = RequestMethod.POST)
	public String getList(ModelMap model){
		String list = employeeService.findAllEmployees();
		return list ;
	}
	
	
	/**
	 * This method will provide the medium to add an employee.
	 */
	@RequestMapping(value = { "/create","/newEmp","/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("empForm", employee);
		Map setupList =  employeeService.getAllSetupEntity();
		model.addAttribute("genderList",setupList.get("genderList"));
		model.addAttribute("marritalStatusList",setupList.get("marritalStatusList"));
		model.addAttribute("nationalityList",setupList.get("nationalityList"));
		
		return "employee/createEmployee";
	}
	
	
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String save(@ModelAttribute("employee") @Valid Employee employee, BindingResult result,
			ModelMap model) {

		System.out.println("Saving data. please wait....");
		if (result.hasErrors()) {
			
			for(ObjectError objectError : result.getAllErrors()){
				System.out.println("\nMessage : "+objectError.getDefaultMessage()+"\nToString : "+objectError.toString());
			}
			
			model.addAttribute("employee",employee);
			
			model.addAttribute("errorMsg",result.getAllErrors().toString());
			
			Map setupList =  employeeService.getAllSetupEntity();
			model.addAttribute("genderList",setupList.get("genderList"));
			model.addAttribute("marritalStatusList",setupList.get("marritalStatusList"));
			model.addAttribute("nationalityList",setupList.get("nationalityList"));
			return "employee/createEmployee";
		}

		
	/*	 * Preferred way to achieve uniqueness of field [PIN] should be implementing custom @Unique annotation 
		 * and applying it on field [PIN] of Model class [Employee].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * */
		 
		if(!employeeService.isEmployeePinUnique(employee.getId(), employee.getPin())){
			FieldError pinError =new FieldError("employee","pin",messageSource.getMessage("non.unique.pin", new String[]{employee.getPin()}, Locale.getDefault()));
		    result.addError(pinError);
		    
		    model.addAttribute("employee",employee);
		    
		    Map setupList =  employeeService.getAllSetupEntity();
			model.addAttribute("genderList",setupList.get("genderList"));
			model.addAttribute("marritalStatusList",setupList.get("marritalStatusList"));
			model.addAttribute("nationalityList",setupList.get("nationalityList"));
			return "employee/createEmployee";
		}
		
		employeeService.saveEmployee(employee);

		//model.addAttribute("successMsg", "Employee " + employee.getFullName() +" ("+ employee.getPin()+")"+ " registered successfully");
		//model.addAttribute("loggedinuser", getPrincipal());
		//return "success";

		 return "redirect:/emp/profile/"+employee.getId();
	}
	
	
	@RequestMapping(value = { "/profile/{id}" }, method = RequestMethod.GET)
	public String profile(@PathVariable("id") String id, ModelMap model) {
		Employee employee =employeeService.findById(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/profile";
	}
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") String id, ModelMap model){
		Map setupList =  employeeService.getEmployeeInfo(Integer.parseInt(id));
		//System.err.println(setupList.get("genderList").);
		model.addAttribute("employee",setupList.get("employee"));
		model.addAttribute("genderList",setupList.get("genderList"));
		model.addAttribute("marritalStatusList",setupList.get("marritalStatusList"));
		model.addAttribute("nationalityList",setupList.get("nationalityList"));
		return "employee/profile";
	}
	@ResponseBody
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
	public String update( @RequestBody Employee employee, BindingResult resultItem) {
		if (resultItem.hasErrors()) {
			return "employee/profile";
		} else{
			return employeeService.update(employee);
		}

	}





}
