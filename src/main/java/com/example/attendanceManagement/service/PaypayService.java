package com.example.attendanceManagement.service;

import java.sql.Date;
import java.util.Optional;

import com.example.attendanceManagement.entity.Paypay;

public interface PaypayService {
	/* 全件取得 */
	Iterable<Paypay> SelectAll();
	
	/*id(主キー)をキーにして1件取得する*/
	Optional<Paypay> SlectOneById(Integer id);
	
	/* 取得したデータをDBにInsertする */
    void Insert(Paypay paypay);
    
    /* データを更新する */
    void Update(Paypay paypay);
    
    void save(Integer i,Integer b,Date d);
}
