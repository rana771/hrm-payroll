package com.bracu.hrm.controller;

import com.bracu.hrm.model.Employee;
import com.bracu.hrm.service.payslip.PaySlipService;
import com.bracu.hrm.util.SQLDataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormatSymbols;
import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;



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
    @RequestMapping(value = { "/show"}, method = RequestMethod.GET)
    public String create(ModelMap model) {

        Map<Integer,String> monthsList = new HashMap<Integer,String>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length -1; i++) {
            String month = months[i];
            monthsList.put((i+1), months[i]);
        }
        ArrayList<String> yearList = new ArrayList<String>();
        for(int years = 2001; years<= Calendar.getInstance().get(Calendar.YEAR); years++) {
            yearList.add(years+"");
        }
       Integer currentMonth =  Calendar.getInstance().get(Calendar.MONTH);
       Integer currentYear =  Calendar.getInstance().get(Calendar.YEAR);
      //  List<Employee> employeeList = employeeService.findAllEmployees();
        model.addAttribute("monthsList", monthsList);
        model.addAttribute("yearList", yearList);
        model.addAttribute("currentMonth", currentMonth+1);
        model.addAttribute("currentYear", currentYear);
        //model.addAttribute("loggedinuser", getPrincipal());
        return "paySlip/paySlip";
    }

    @RequestMapping(value = { "/printAll/{salaryType}/{pinNo}/{salaryMonth}/{salaryYear}"}, method = RequestMethod.GET)
    public ModelAndView printAll(@PathVariable("salaryType") String salaryType
            , @PathVariable("pinNo") String pinNo, @PathVariable("salaryMonth") String salaryMonth
            , @PathVariable("salaryYear") String salaryYear){
        JasperReportsPdfView view = new JasperReportsPdfView();
        Map<String, Object> params = new HashMap<>();
        try {
            
            view.setUrl("classpath:reports/paySlip/paySlipMaster.jasper");
            view.setApplicationContext(appContext);

            Map<String, String> pathVariable = new HashMap<String,String>();
            pathVariable.put("salaryType",salaryType);
            pathVariable.put("pinNo",pinNo);
            pathVariable.put("salaryMonth",salaryMonth);
            pathVariable.put("salaryYear",salaryYear);

        params.put("masterReportTitle", "Requisition List");
        params.put("masterCurrentUser", "Nahid Hasan");
        params.put("SUBREPORT_DIR", "classpath:reports/subreports/");
        params.put("transactionDateFromDMY", "01-01-2017");
        params.put("transactionDateToDMY",   "10-01-2017");
        params.put("title",   "test");
        params.put("companyLogo",   "classpath:reports/subreports/bracu_logo.png");
        params.put("datasource",new JRResultSetDataSource( paySlipService.findAll(pathVariable)));


      //  return "";

             //  String exportDir = System.getProperty("F:\\project\\hr-payroll\\hrm-payroll\\src\\main\\resources\\reports\\tempReports") ;
              // String exportPath = exportDir + "/paySlipMaster.pdf";
              // JasperReport jasperReport = JasperCompileManager.compileReport("F:\\project\\hr-payroll\\hrm-payroll\\src\\main\\resources\\reports\\paySlip\\paySlipMaster.jasper");

            //JasperPrint jasperPrint = JasperFillManager.fillReport( this.getClass().getClassLoader().getResourceAsStream("\"F:\\project\\hr-payroll\\hrm-payroll\\src\\main\\resources\\reports\\paySlip\\paySlipMaster.jasper"), null,new JRResultSetDataSource( paySlipService.findAll(pathVariable)));
              // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JRResultSetDataSource( paySlipService.findAll(pathVariable)));
               //JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
             //  JasperExportManager.exportReportToPdfFile(jasperPrint, exportPath);
        //Resource report = new ClassPathResource("classpath:reports/paySlip/paySlipMaster.jasper");

       // JasperPrint jasperPrint = JasperFillManager.fillReport(report.getInputStream(), Collections.EMPTY_MAP,ds)
           // ByteArrayOutputStream baos = new ByteArrayOutputStream();

          //  JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
           // DataSource aAttachment =  new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
           // byte[] pdfByteArray = JasperExportManager.exportReportToPdf(jasperPrint);
            //ByteArrayInputStream arrayInputStream=  new ByteArrayInputStream(pdfByteArray);
        
       // JRDataSource ds = new JRResultSetDataSource( paySlipService.findAll(pathVariable));

        //InputStream is=this.getClass().getResourceAsStream("classpath:reports/paySlip/paySlipMaster.jrxml");
       InputStream input = new FileInputStream(new File("/Users/rana/Works/eclipse-workspace/hrm-payroll/hrm-master/src/main/resources/reports/paySlip/paySlipMaster.jrxml"));

        		JasperReport jasperReport = JasperCompileManager.compileReport(input);
        //Resource report = new ClassPathResource(view.getUrl()
       // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, Collections.EMPTY_MAP,ds);
      //  ByteArrayOutputStream baos = new ByteArrayOutputStream();
       // JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
      //  DataSource attachment =  new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

        
        
           // MimeBodyPart attachment = new MimeBodyPart(dss);
          //  attachment.setHeader("Content-Type", "application/pdf");
           // mimeMultipart.addBodyPart(attachment);


     // get the JRXML template as a stream
        //InputStream template = this.getClass().getResourceAsStream("/sampleReport.xml");
        // compile the report from the stream
        //JasperReport report = JasperCompileManager.compileReport(template);
        // fill out the report into a print object, ready for export. 
        JasperPrint print = JasperFillManager.fillReport(jasperReport, (Map)new HashMap<String, String>(),new JRResultSetDataSource( paySlipService.findAll(pathVariable)));
        // export it!
        File pdf = File.createTempFile("output.", ".pdf");
        //JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
     
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(print, baos);
         DataSource attachment =  new ByteArrayDataSource(baos.toByteArray(), "application/pdf");

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject("Test message payslip");
            helper.setText("Hello body");
            helper.setTo("rana771@gmail.com");
            helper.setFrom("erp@bracu.ac.bd");

            helper.addAttachment("payslip.pdf", pdf);
            emailSender.send(message);
        }catch (Exception e)
           {
               e.printStackTrace();
           }
        return new ModelAndView(view, params);
    }

    @Autowired
    private JavaMailSender emailSender;
}
