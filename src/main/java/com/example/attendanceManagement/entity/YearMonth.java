package com.example.attendanceManagement.entity;

import java.time.Month;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // Getter,Setterが不要になる
@NoArgsConstructor  // デフォルトコンストラクターの自動生成
@AllArgsConstructor // 全フィールドに対する初期化値を引数に取るコンストラクタを自動生成
public class YearMonth  {
	/*@id*/
	private Integer YearMonth;

	public Object getMonthValue() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public static YearMonth of(Month month, int i) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public static YearMonth of(Integer year, Integer month) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
