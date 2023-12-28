package com.example.attendanceManagement.service;

import java.util.Optional;

import com.example.attendanceManagement.entity.Shift_table;

public interface Shift_tableService {


	/* 全件取得 */
	Iterable<Shift_table> SelectAll();
	
	/*id(主キー)をキーにして1件取得する*/
	Optional<Shift_table> SlectOneById(Integer id);
	
	/* 取得したデータをDBにInsertする */
    void Insert(Shift_table shift_table);
     
    /* データを更新する */
    void Update(Shift_table shift_table);
    /*idとmonthで選択したテーブルを取得*/
    Iterable<Shift_table> idMonthAll(Integer employee_id,String month);
    /*idとmonthでusernameを取得*/
    String idMonthUsernameSelect(Integer employee_id,String month);

	/*employee_idとyearmonthでUpdateする*/
    void Shiftup(Shift_table shift_table);
    
    
    
}
