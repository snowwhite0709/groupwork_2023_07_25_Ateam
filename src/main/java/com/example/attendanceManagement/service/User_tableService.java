package com.example.attendanceManagement.service;

import java.util.Optional;

import com.example.attendanceManagement.entity.User_table;

public interface User_tableService {

	/* 全件取得 */
	Iterable<User_table> SelectAll();
	
	/*id(主キー)をキーにして1件取得する*/
	Optional<User_table> SlectOneById(Integer id);
	
	/* 取得したデータをDBにInsertする */
    void Insert(User_table user_table);
    
    /* データを更新する */
    void Update(User_table user_table);
}
