package com.bracu.hrm.controller;

import com.bracu.hrm.model.org.Department;
import com.bracu.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/dept")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	MessageSource messageSource;
	
	@RequestMapping(value = { "/new", "/create","/view" }, method = RequestMethod.GET)
	public String create(ModelMap model) {

//		List<Employee> employeeList = employeeService.findAllEmployees();
//		model.addAttribute("employeeList", employeeList);
		//model.addAttribute("loggedinuser", getPrincipal());
		return "department/departmentForm";
	}

	@ResponseBody
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String save(@RequestBody Department department, BindingResult result, ModelMap model, Principal principal) {
		String message;
//		model.addAttribute("id",1);
//		model.addAttribute("version", 0 );
		if(result.hasErrors()){
			return "department/create";
		}
		else{
			if(!departmentService.uniqueNameCheck(department.getId(), department.getName())){
				message = messageSource.getMessage("non.unique.name", new String[]{"Department", department.getName()}, Locale.getDefault());
				return message;
			}
			else{
				departmentService.save(department,principal);

			}

		}

		message = messageSource.getMessage("save.successful.message", new String[]{"Department", department.getName()}, Locale.getDefault());
		return message;

	}

	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String getDepartmentList(ModelMap model){
		String subGroupJson = departmentService.getDepartmentList();
		return subGroupJson ;
	}

    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String edit(@PathVariable("id") String id, ModelMap model){
        String subGroupJson = departmentService.getDepartmentById(Integer.parseInt(id));
        System.err.println(subGroupJson);
        return subGroupJson ;
    }

	@ResponseBody
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST)
	public String update( @RequestBody Department department, BindingResult resultItem) {
		System.err.println(department);
		if (resultItem.hasErrors()) {
			return "dept/create";
		} else{
			return departmentService.update(department);
		}

		//return message;
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") String id, ModelMap model){
		String subGroupJson = departmentService.delete(Integer.parseInt(id));
		return subGroupJson ;
	}

}
