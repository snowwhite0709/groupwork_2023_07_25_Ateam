package com.example.attendanceManagement.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // Getter,Setterが不要になる
@NoArgsConstructor  // デフォルトコンストラクターの自動生成
@AllArgsConstructor // 全フィールドに対する初期化値を引数に取るコンストラクタを自動生成
public class Shift_table {
	 @Id
	private Integer id;
	private Integer employee_id;
	private String username;
	private String yearmonth;
	private Boolean workday;
	private Integer variableattendance;
	private Integer kind;
	private Boolean approval;
	private String month;
	

}