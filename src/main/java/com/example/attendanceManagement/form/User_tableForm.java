package com.example.attendanceManagement.form;

import lombok.Data;
@Data
public class User_tableForm {

	private Integer id;
	private String pass;
	private String lastname;
	private String firstname;
	private Integer sex;
	private Integer age;
	private Integer status;
	private Integer rank;
	private Integer admin;
	
	private Boolean newUser_table;
}