package com.bracu.hrm.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import com.bracu.hrm.dao.SetupEntityDao;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.service.DepartmentService;
import com.bracu.hrm.service.EmployeeService;
import com.sun.jmx.snmp.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.bracu.hrm.service.payslip.PaySlipService;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.export.PdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Created by Nahid on 07-Jan-18.
 */
@Controller
@RequestMapping("/payment")
public class PaySlipController {
	@Autowired
	PaySlipService paySlipService;
	@Autowired
	DepartmentService departmentService;


	@RequestMapping(value = { "/show" }, method = RequestMethod.GET)
	public String create(ModelMap model) {

		Map<Integer, String> monthsList = new HashMap<Integer, String>();
		String[] months = new DateFormatSymbols().getMonths();
		for (int i = 0; i < months.length - 1; i++) {
			String month = months[i];
			monthsList.put((i + 1), months[i]);
		}
		ArrayList<String> yearList = new ArrayList<String>();
		for (int years = 2001; years <= Calendar.getInstance().get(Calendar.YEAR); years++) {
			yearList.add(years + "");
		}
		Integer currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
		// List<Employee> employeeList = employeeService.findAllEmployees();
		model.addAttribute("monthsList", monthsList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("currentMonth", currentMonth + 1);
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("departmentList",departmentService.findAll());
		// model.addAttribute("loggedinuser", getPrincipal());
		return "paySlip/paySlip";
	}

	@RequestMapping(value = { "/printAll/{salaryType}/{pinNo}/{salaryMonth}/{salaryYear}/{printOrEmail}/{departmentId}" }, method = RequestMethod.GET)
	public ModelAndView printAll(@PathVariable("salaryType") String salaryType, @PathVariable("pinNo") String pinNo,
			@PathVariable("salaryMonth") String salaryMonth, @PathVariable("salaryYear") String salaryYear,
			@PathVariable("printOrEmail") String printOrEmail, @PathVariable("departmentId") String departmentId) {

		Map criteria = new HashMap<String,String>();
		criteria.put("salaryType",salaryType.toString());
		criteria.put("pinNo",pinNo.toString());
		criteria.put("salaryMonth",salaryMonth.toString());
		criteria.put("salaryYear",salaryYear.toString());
		criteria.put("printOrEmail",printOrEmail.toString());
		criteria.put("departmentId",departmentId.toString());

		return paySlipService.generatePaySlip(criteria);

	}
	@RequestMapping(value = { "/mailList" }, method = RequestMethod.POST)
	public String list(ModelMap model) {
		String subGroupJson = paySlipService.getMailList();
		return subGroupJson ;
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listPage(ModelMap model) {
		return "paySlip/paySlipList";
	}


}
