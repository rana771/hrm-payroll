package com.bracu.hrm.service.payslip;

import com.bracu.hrm.dao.CompanyDao;
import com.bracu.hrm.dao.MailDao;
import com.bracu.hrm.dao.PaySlipDao;
import com.bracu.hrm.dao.SetupEntityDao;
import com.bracu.hrm.model.Employee;
import com.bracu.hrm.model.email.Mail;
import com.bracu.hrm.model.org.Company;
import com.bracu.hrm.service.CompanyService;
import com.bracu.hrm.service.EmployeeService;
import com.bracu.hrm.service.email.EmailService;
import com.bracu.hrm.util.SQLDataSource;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.lowagie.text.pdf.PdfWriter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import javax.activation.DataSource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormatSymbols;
import java.util.*;


@Service("paySlipService")
@Transactional
public class PaySlipServiceImpl implements PaySlipService {

	@Autowired
	private PaySlipDao paySlipDao;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	SetupEntityDao setupEntityDao;
	@Autowired
	PaySlipService paySlipService;
	@Autowired
	MailDao mailDao;
//	public Company findById(int id) {
//		return companyDao.findById(id);
//	}
//
//	public Company findByName(String name) {
//		Company company = companyDao.findByName(name);
//		return company;
//	}
//
//	public void save(Company company) {
//		companyDao.save(company);
//	}
//
//	public void update(Company company) {
//		Company entity = companyDao.findById(company.getId());
//		if(entity!=null){
//			entity.setVersion(company.getVersion()+1);
//			entity.setName(company.getName());
//			entity.setShortName(company.getShortName());
//			entity.setEmail(company.getEmail());
//			entity.setWebUrl(company.getWebUrl());
//			entity.setEmail(company.getEmail());
//			entity.setPhone1(company.getPhone1());
//			entity.setPhone2(company.getPhone2());
//			entity.setFax(company.getFax());
//			entity.setAddress(company.getAddress());
//			entity.setCompanyLogo(company.getCompanyLogo());
//		}
//	}
//
//
//	public void delete(int id) {
//		companyDao.delete(id);
//	}

	public ResultSet findAll(Map params) {
		return paySlipDao.findAll(params);
	}

//	public boolean isUnique(Integer id, String name) {
//		Company user = findByName(name);
//		return ( user == null || ((id != null) && (user.getId() == id)));
//	}

	public ModelAndView generatePaySlip(Map criteria){
		JasperReportsPdfView view = new JasperReportsPdfView();
		Map<String, Object> params = new HashMap<>();
		try {
			Map<String, String> pathVariable = new HashMap<String, String>();
			pathVariable.put("salaryType", criteria.get("salaryType").toString());
			pathVariable.put("pinNo", criteria.get("pinNo").toString());
			pathVariable.put("salaryMonth", criteria.get("salaryMonth").toString());
			pathVariable.put("salaryYear", criteria.get("salaryYear").toString());
			pathVariable.put("departmentId", criteria.get("departmentId").toString());
			if(criteria.get("printOrEmail").toString().equals("print")) {
				view.setUrl("classpath:reports/paySlip/paySlipMaster.jasper");
				view.setApplicationContext(appContext);
				params.put("masterReportTitle", "Requisition List");
				params.put("masterCurrentUser", "Nahid Hasan");
				params.put("SUBREPORT_DIR", "classpath:reports/subreports/");
				params.put("transactionDateFromDMY", "01-01-2017");
				params.put("transactionDateToDMY", "10-01-2017");
				params.put("title", "test");
				params.put("companyLogo", "classpath:reports/subreports/bracu_logo.png");
				params.put("datasource", new JRResultSetDataSource(paySlipService.findAll(pathVariable)));
			}else {
				view.setUrl("classpath:reports/paySlip/paySlipMaster.jasper");
				view.setApplicationContext(appContext);


				params.put("masterReportTitle", "Requisition List");
				params.put("masterCurrentUser", "Nahid Hasan");
				params.put("SUBREPORT_DIR", "classpath:reports/subreports/");
				params.put("transactionDateFromDMY", "01-01-2017");
				params.put("transactionDateToDMY", "10-01-2017");
				params.put("title", "test");
				params.put("companyLogo", "classpath:reports/subreports/bracu_logo.png");
				params.put("datasource", new JRResultSetDataSource(paySlipService.findAll(pathVariable)));

				ResultSet rs = paySlipService.findAll(pathVariable);

				ResultSetMetaData md = rs.getMetaData();
				Set<String> uniquePinList = new HashSet<String>();
				while (rs.next()){
					uniquePinList.add(rs.getString("pin"));
				}

				for (String pin : uniquePinList) {
					Employee employee = employeeService.findByPin(pin);
					if(employee!=null) {
						if(employee.getEmail().contains("@")) {
							Map<String, String> emailVariable = new HashMap<String, String>();
							pathVariable.put("salaryType", "1");
							pathVariable.put("pinNo", pin);
							pathVariable.put("salaryMonth", criteria.get("salaryMonth").toString());
							pathVariable.put("salaryYear", criteria.get("salaryYear").toString());
							pathVariable.put("departmentId", criteria.get("departmentId").toString());
							//InputStream input = new FileInputStream(new File("/Users/rana/Works/eclipse-workspace/hrm-payroll/src/main/resources/reports/paySlip/paySlipMaster.jrxml"));
							InputStream input = new ClassPathResource("reports/paySlip/paySlipMaster.jrxml").getInputStream();

							JasperReport jasperReport = JasperCompileManager.compileReport(input);

							Map parameters = new HashMap<>(params);
							JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters,
									new JRResultSetDataSource(paySlipService.findAll(pathVariable)));


							ByteArrayOutputStream baos = new ByteArrayOutputStream();

							JRPdfExporter exporter = new JRPdfExporter();
							exporter.setExporterInput(new SimpleExporterInput(print));
							exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
							SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
							configuration.setEncrypted(true);
							configuration.set128BitKey(true);
							configuration.setUserPassword(pin);
							configuration.setOwnerPassword("reports");
							configuration.setPermissions(PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING);
							exporter.setConfiguration(configuration);
							exporter.exportReport();
							DataSource attachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
							MimeMessage message = emailSender.createMimeMessage();
							message.setFrom(new InternetAddress("BRAC University<noreply@bracu.ac.bd>"));
							
							MimeMessageHelper helper = new MimeMessageHelper(message, true);

							helper.setSubject("Payslip For The Month Of "+new DateFormatSymbols().getMonths()[Integer.parseInt(criteria.get("salaryMonth").toString())-1]+", "+criteria.get("salaryYear").toString());
							helper.setText("Dear " + employee.getFullName()+", \r\n \r\nPlease find the payslip for the month of " + new DateFormatSymbols().getMonths()[Integer.parseInt(criteria.get("salaryMonth").toString())-1] +
									", "+criteria.get("salaryYear").toString()+" as attached file. \r\n \r\n N.B. This Payslip is machine generated and" +
									" password protected pdf file. Please use your 8 digit PIN No. to open. (e.g : 00000001).");
							helper.setTo(employee.getEmail().toString());
							helper.setFrom("BRAC University <noreply@bracu.ac.bd>");

							helper.addAttachment(new DateFormatSymbols().getMonths()[Integer.parseInt(criteria.get("salaryMonth").toString())-1]+"_"+criteria.get("salaryYear").toString()+"_Payslip.pdf", attachment);
							emailSender.send(message);
						}else {
							Mail mail = new Mail();
							mail.setFrom("BRAC University <erp@bracu.ac.bd>");
							mail.setTo(employee.getPin().toString());
							mail.setSubject("No Email Address Found.");
							mail.setContent("Dear " + employee.getFullName()+", \r\nPlease Find the Payslip for the month of " + new DateFormatSymbols().getMonths()[Integer.parseInt(criteria.get("salaryMonth").toString())-1] +
									", "+criteria.get("salaryYear").toString()+" as attached file. \r\n \r\n \r\n N.B. This Payslip is machine generated and" +
									" password protected pdf file. Please use your 8 digit PIN no to open it. (e.g : 00000001).");
							mail.setNote("No Email Address Found For " +  employee.getPin().toString() + " - "+ employee.getFullName().toString());
							mail.setStatus(false);
							mail.setSalaryMonth(criteria.get("salaryMonth").toString());
							mail.setSalaryYear(criteria.get("salaryYear").toString());
							mailDao.save(mail);
						}

					}else {
						ResultSet newEmployee = employeeService.getSqlServerEmployee(pin);

						ResultSetMetaData md2 = newEmployee.getMetaData();
						int columns = md2.getColumnCount();
						ArrayList list = new ArrayList(newEmployee.getRow());
						while (newEmployee.next()){
							HashMap row = new HashMap(columns);
							for(int i=1; i<=columns; ++i){
								row.put(md2.getColumnName(i),newEmployee.getObject(i));
							}
							list.add(row);
						}
						Object empObj = list.get(0);
						employeeService.prepareNewEmployeeFromPaySlip(empObj);

					}
				}

			}
			Connection con  = new SQLDataSource().getSqlConnection();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 	new ModelAndView(view, params);
	}

	public String getMailList() {
		List list = paySlipDao.getRequisitionList();
		String resultJson = "";
		try {
			Gson gson = new Gson();
			resultJson = gson.toJson(list);
		}catch (JsonIOException e){
			System.out.println(e.getMessage());
		}catch (RuntimeException e){
			System.out.println(e.getMessage());
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return resultJson;
	}

}
