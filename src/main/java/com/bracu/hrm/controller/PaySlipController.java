package com.bracu.hrm.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

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

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 * Created by Nahid on 07-Jan-18.
 */
@Controller
@RequestMapping("/payment")
public class PaySlipController {
	@Autowired
	PaySlipService paySlipService;
	@Autowired
	ApplicationContext appContext;

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
		// model.addAttribute("loggedinuser", getPrincipal());
		return "paySlip/paySlip";
	}

	@RequestMapping(value = { "/printAll/{salaryType}/{pinNo}/{salaryMonth}/{salaryYear}" }, method = RequestMethod.GET)
	public ModelAndView printAll(@PathVariable("salaryType") String salaryType, @PathVariable("pinNo") String pinNo,
			@PathVariable("salaryMonth") String salaryMonth, @PathVariable("salaryYear") String salaryYear) {
		JasperReportsPdfView view = new JasperReportsPdfView();
		Map<String, Object> params = new HashMap<>();
		try {

			view.setUrl("classpath:reports/paySlip/paySlipMaster.jasper");
			view.setApplicationContext(appContext);

			Map<String, String> pathVariable = new HashMap<String, String>();
			pathVariable.put("salaryType", salaryType);
			pathVariable.put("pinNo", pinNo);
			pathVariable.put("salaryMonth", salaryMonth);
			pathVariable.put("salaryYear", salaryYear);

			params.put("masterReportTitle", "Requisition List");
			params.put("masterCurrentUser", "Nahid Hasan");
			params.put("SUBREPORT_DIR", "classpath:reports/subreports/");
			params.put("transactionDateFromDMY", "01-01-2017");
			params.put("transactionDateToDMY", "10-01-2017");
			params.put("title", "test");
			params.put("companyLogo", "classpath:reports/subreports/bracu_logo.png");
			params.put("datasource", new JRResultSetDataSource(paySlipService.findAll(pathVariable)));
			InputStream input = new FileInputStream(new File(
					"/Users/rana/Works/eclipse-workspace/hrm-payroll/hrm-master/src/main/resources/reports/paySlip/paySlipMaster.jrxml"));

			JasperReport jasperReport = JasperCompileManager.compileReport(input);

			Map parameters = new HashMap<>(params);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters,
					new JRResultSetDataSource(paySlipService.findAll(pathVariable)));
			// export it!
			File pdf = File.createTempFile("output.", ".pdf");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(print, baos);
			DataSource attachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setSubject("Test message payslip");
			helper.setText("Hello body");
			helper.setTo("rana771@gmail.com");
			helper.setFrom("erp@bracu.ac.bd");

			helper.addAttachment("payslip.pdf", attachment);
			emailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView(view, params);
	}

	@Autowired
	private JavaMailSender emailSender;
}
