package com.example.attendanceManagement.service;

import java.sql.Date;
import java.util.Optional;

import com.example.attendanceManagement.entity.Work;

public interface WorkService {
	/* 全件取得 */
	Iterable<Work> SelectAll();
	
	/*id(主キー)をキーにして1件取得する*/
	Optional<Work> SlectOneById(Integer id);
	
	/* 取得したデータをDBにInsertする */
    void InsertWork(Work work);
    
    /* データを更新する */
    void UpdateWork(Work work);
    
    //承認がNULLのものを表示するためのメソッド
    Iterable<Work> getWork();
    
    /*id(主キー)と日付をキーにして1件取得する*/
	Optional<Work> selectW(Integer employee_id,Date day);
	
	//Employee IDで一件取得する
	Optional<Work> selectW2(Integer employee_id);
}