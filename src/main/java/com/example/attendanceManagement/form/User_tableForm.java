package com.example.attendanceManagement.form;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class User_tableForm {

	private Integer id;
	
	@Size(min=8,max=12)
	private String pass;
	private String lastname;
	private String firstname;
	
	private Integer sex;
	
	@Range(min=1, max=100)
	private Integer age;
	private Integer status;
	private Integer rank;
	private Integer admin;
	
	private Boolean newUser_table;
}
