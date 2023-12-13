package com.example.attendanceManagement.service;

import java.util.Optional;

import com.example.attendanceManagement.entity.Kind;


public interface KindService {

	/* 全件取得 */
	Iterable<Kind> SelectAll();
	
	/*id(主キー)をキーにして1件取得する*/
	Optional<Kind> SlectOneById(Integer id);
	
	/* 取得したデータをDBにInsertする */
    void Insert(Kind kind);
    
    /* データを更新する */
    void Update(Kind kind);
}
