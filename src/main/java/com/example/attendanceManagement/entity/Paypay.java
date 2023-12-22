package com.example.attendanceManagement.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // Getter,Setterが不要になる
@NoArgsConstructor  // デフォルトコンストラクターの自動生成
@AllArgsConstructor // 全フィールドに対する初期化値を引数に取るコンストラクタを自動生成
public class Paypay {
	@Id
	private Integer id;
	private Integer employee_id;
	private Integer basepay;
	private Date day;
}
