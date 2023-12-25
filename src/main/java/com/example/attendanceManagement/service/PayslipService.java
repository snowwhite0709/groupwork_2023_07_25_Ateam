package com.example.attendanceManagement.service;

import java.sql.Date;
import java.time.YearMonth;
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

	Object getPayDetails(YearMonth selectedMonth);

	//Employee IDでIterableで取得する
	Iterable<Payslip> selectI(Integer employee_id);

    void in(Integer i,Integer b,Integer o,Date d);
    
    void up(Integer b,Integer o,Integer i);
    
    void upoverpay(Integer o,Integer i);
}
