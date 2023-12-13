package com.example.attendanceManagement.service;

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
    
    Iterable<Work> getWork();
}
