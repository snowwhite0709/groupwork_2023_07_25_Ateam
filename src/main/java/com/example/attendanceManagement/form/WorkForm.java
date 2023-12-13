package com.example.attendanceManagement.form;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class WorkForm {
	private Integer id;
	private Timestamp day;
	private Timestamp attendancetime;
	private Timestamp leavingtime;
	private Timestamp overtime;
	private Integer kind;
	private boolean approval;
}
