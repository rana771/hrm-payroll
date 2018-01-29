package com.bracu.hrm.dao;

import com.bracu.hrm.model.email.Mail;
import com.bracu.hrm.util.SQLDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


@Repository("PaySlipDao")
public class PaySlipDaoImpl extends AbstractDao<Integer, Mail>  implements PaySlipDao {

	static final Logger logger = LoggerFactory.getLogger(PaySlipDaoImpl.class);

	@SuppressWarnings("unchecked")
	public ResultSet findAll(Map params) {
		Connection con  = new SQLDataSource().getSqlConnection();
		List allResult = new ArrayList();
		String pinNo = " 1=1 AND ";
		String departmentId = " 1=1 AND ";
		if(Integer.parseInt(params.get("salaryType").toString())==1){
			pinNo = " hr_employee_t.pin =RIGHT(REPLICATE('0', 8) + '"+params.get("pinNo")+"', 8) AND ";
		}
		if((params.get("departmentId").toString()).equals("ALL")){
			departmentId = " 1=1 AND ";
		}else {
			departmentId = " hr_employee_t.section_code ="+params.get("departmentId")+" AND ";

		}


		ResultSet rs = null;
		try
		{
			con = new SQLDataSource().getSqlConnection();
			Statement s1 = con.createStatement();
			String sqlString = "select\n" +
					" ISNULL(aa.id, bb.id) id,\n" +
					" ISNULL(aa.serialNo, bb.serialNo) serialNo,\n" +
					" ISNULL(aa.salaryNumber, '') salaryNumber,\n" +
					"  ISNULL(aa.name, '') name,\n" +
					"\tISNULL(aa.pin, bb.pin2) pin,\n" +
					"\tISNULL(aa.designation, '') designation,\n" +
					"\tISNULL(aa.breakupName, '') breakupName,\n" +
					"\tISNULL(aa.amount, 00.00) amount,\n" +
					"\tISNULL(aa.pay_date, null)pay_date,\n" +
					"\tISNULL(aa.pf_id, '') pf_id,\n" +
					"\tISNULL(aa.paymentMode, '') paymentMode,\n" +
					"\tISNULL(aa.salaryMonth, '') salaryMonth,\n" +
					"\tISNULL(aa.salaryYear, '') salaryYear,\n" +
					"\tISNULL(aa.deparment, '') deparment,\n" +
					"\tISNULL(bb.id, null) bbId,\n"+
					"\tISNULL(bb.serialNo, null) serialNo2,\n"+
					"\tISNULL(bb.deductName, '') deductName,\n"+
					"\tISNULL(bb.deductAmount, 0.00) deductAmount\n"+
					"\n" +
					"from (\n" +
					"\n" +
					"SELECT\n" +
					"hr_employee_t.id id,\n" +
					"row_number() over(partition by hr_employee_t.id order by hr_employee_t.id) serialNo,\n" +
					"pr_employee_salary_generation_parent_t.number salaryNumber ,\n" +
					"hr_employee_t.name,hr_employee_t.pin ,hr_designation_t.name designation,\n" +
					"pr_salary_breakup_t.name breakupName,pr_employee_salary_generation_child_employee_breakup_t.amount,\n" +
					"CONVERT(VARCHAR(10), pr_employee_salary_generation_parent_t.pay_date, 105) AS pay_date,\n" +
					"pf_employee_information_t.pf_id,\n" +
					"pr_payment_mode_t.name paymentMode,\n" +
					"DateName( month , DateAdd( month , pr_employee_salary_generation_parent_t.salary_for_the_month , 0 ) - 1 ) salaryMonth, pr_employee_salary_generation_parent_t.salary_for_the_year salaryYear,\n" +
					"section_t.name deparment\n" +
					"\n" +
					"FROM hr_employee_t\n" +
					"INNER JOIN hr_designation_t ON hr_designation_t.code =hr_employee_t.designation_code\n" +
					"INNER JOIN pr_employee_salary_generation_parent_t ON\n" +
					"pr_employee_salary_generation_parent_t.employee_id = hr_employee_t.id\n" +
					"INNER JOIN pr_employee_salary_generation_child_employee_breakup_t ON\n" +
					"pr_employee_salary_generation_child_employee_breakup_t.number =pr_employee_salary_generation_parent_t.number\n" +
					"INNER JOIN pr_salary_breakup_t ON\n" +
					"pr_salary_breakup_t.code =pr_employee_salary_generation_child_employee_breakup_t.salary_breakup_id\n" +
					"LEFT JOIN pf_employee_information_t on pf_employee_information_t.employee_id = hr_employee_t.id\n" +
					"INNER JOIN pr_payment_mode_t on pr_payment_mode_t.code = pr_employee_salary_generation_parent_t.payment_mode_code\n" +
					"INNER JOIN section_t on section_t.code = hr_employee_t.section_code\n" +
					"\n" +
					"WHERE "+ departmentId + pinNo +" pr_employee_salary_generation_parent_t.salary_for_the_year='"+params.get("salaryYear") +"' and pr_employee_salary_generation_parent_t.salary_for_the_month='"+params.get("salaryMonth") +"'\n" +
					") as aa\n" +
					"full join (\n" +
					"\n" +
					"SELECT\n" +
					"pr_employee_salary_generation_child_employee_deduction_t.employee_id id,hr_employee_t.pin pin2,\n" +
					"row_number() over(partition by pr_employee_salary_generation_parent_t.employee_id  order by pr_employee_salary_generation_parent_t.employee_id ) serialNo,\n" +
					"pr_salary_deduction_breakup_t.name deductName,\n" +
					"pr_employee_salary_generation_child_employee_deduction_t.amount deductAmount\n" +
					"\n" +
					"FROM pr_employee_salary_generation_parent_t\n" +
					"INNER JOIN  hr_employee_t on hr_employee_t.id = pr_employee_salary_generation_parent_t.employee_id\n" +
					"\n" +
					"INNER JOIN pr_employee_salary_generation_child_employee_deduction_t on\n" +
					"pr_employee_salary_generation_child_employee_deduction_t.number =\n" +
					"pr_employee_salary_generation_parent_t.number\n" +
					"INNER JOIN pr_salary_deduction_breakup_t on  pr_salary_deduction_breakup_t.code =\n" +
					"pr_employee_salary_generation_child_employee_deduction_t.salary_deduction_breakup_code\n" +
					"WHERE "+ departmentId + pinNo +" pr_employee_salary_generation_parent_t.salary_for_the_year='"+params.get("salaryYear")+"' and pr_employee_salary_generation_parent_t.salary_for_the_month='"+params.get("salaryMonth")+"'\n" +
					"\n" +
					") as bb\n" +
					"on aa.id = bb.id and aa.serialNo = bb.serialNo\n" +
					"order by pin";
			 rs = s1.executeQuery(sqlString);

			//allResult = Utility.convertResultSetToList(rs);
			//con.close();
			//String result = new result[20];

		}catch (SQLException e) {


		}catch
				(Exception e)
		{
			e.printStackTrace();
		}


		return rs;
	}
//	public Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
//		public int compare(Map<String, String> m1, Map<String, String> m2) {
//			return m1.get("id").compareTo(m2.get("id"));
//		}
//	};



	public List getRequisitionList() {
		List list = new ArrayList<>();
		String sql = "SELECT mail.id, mail.content, mail.note,"+
		"case when mail.status = false then 'Failed' else 'Success' end status"+
				",concat(to_char(to_timestamp(cast(mail.salary_month as char), 'MM'), 'Month'), ', ' , mail.salary_year) \"monthAndYear\" FROM mail";
		String a="select \n" +
			"mail.id,\n" +
				"mail.content content,\n" +
			"mail.content \"content\",\n" +
			"mail.note,\n" +
			"case when mail.status = false then 'Failed' else 'Success' end status,\n" +
			"concat(to_char(to_timestamp(to_char(mail.salary_month::int, '999'), 'MM'), 'Month'), ', ' , mail.salary_year) \"monthAndYear\"\n" +
			"  \n" +
			"from mail order by id desc\n" +
			"\n";
		try {

			return executeSQL(sql);
		} catch	(Exception e) {
			e.printStackTrace();
		}
		return  list;
	}
}
