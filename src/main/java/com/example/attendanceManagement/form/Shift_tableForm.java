package com.example.attendanceManagement.form;

import lombok.Data;

@Data
public class Shift_tableForm {
	 
	private Integer id;
	private Integer employee_id;
	private String username;
	
	private String yearmonth;
	private Boolean workday;
	
	private Integer variableattendance;
	private Integer kind;
	
	private Boolean approval;
	private String month;
	
	private Boolean newShift_table;
}
