package com.example.attendanceManagement.form;

import java.sql.Date;

import lombok.Data;

@Data
public class WorkForm {
	private Integer id;
	private Date day;
	private Date attendancetime;
	private Date leavingtime;
	private Date overtime;
	private Integer kind;
	private boolean approval;
}
