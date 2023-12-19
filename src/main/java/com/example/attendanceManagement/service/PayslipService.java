package com.example.attendanceManagement.service;

import java.util.Optional;

import com.example.attendanceManagement.entity.Payslip;

public interface PayslipService {
	
	/* 全件取得 */
	Iterable<Payslip> SelectAll();
	
	/*id(主キー)をキーにして1件取得する*/
	Optional<Payslip> SlectOneById(Integer id);
	
	/* 取得したデータをDBにInsertする */
    void Insert(Payslip paylist);
    
    /* データを更新する */
    void Update(Payslip paylist);

}
