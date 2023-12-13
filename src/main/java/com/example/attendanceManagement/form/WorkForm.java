package com.example.attendanceManagement.form;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class WorkForm {
	private Integer id;
	private Timestamp attendance;
	private Timestamp leaving;
	private Integer kind;
}
