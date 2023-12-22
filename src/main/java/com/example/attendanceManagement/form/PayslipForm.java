package com.example.attendanceManagement.form;

import java.sql.Date;

import lombok.Data;
@Data
public class PayslipForm {
	
	private Integer employee_id;
	private Integer basepay;
	private Date day;
}



