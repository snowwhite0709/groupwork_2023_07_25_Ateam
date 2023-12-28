package com.example.attendanceManagement.repository;

import java.sql.Date;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.attendanceManagement.entity.Payslip;

public interface PayslipRepository extends CrudRepository<Payslip, Integer> {
    // 任意のクエリメソッドやカスタムメソッドを追加することができます
    // 例：List<PayslipEntity> findByFirstname(String firstname);
	/*社員IDからとってくる*/
	@Query("SELECT * FROM payslip WHERE employee_id=:employee_id")
	Iterable<Payslip> selectI(
			@Param("employee_id") Integer employee_id);
	
	@Modifying
	@Query("INSERT INTO payslip (employee_id, basepay, overtimepay,total, day) "
    		+ "VALUES (:employee_id, :basepay, :overtimepay, :total, :day)")
    void inpaypay(@Param("employee_id") Integer employee_id, @Param("basepay") Integer basepay, 
    		@Param("overtimepay") Integer overtimepay, @Param("total") Integer total, @Param("day") Date day);
	
	@Modifying
	@Query("UPDATE payslip SET basepay = :basepay ,overtimepay = :overtimepay,total = :total where id = :id ")
    void uppaypay(@Param("basepay") Integer basepay,@Param("overtimepay") Integer overtimepay, 
    		 @Param("total") Integer total, @Param("id") Integer id);
	
	//残業代保存
	@Modifying
	@Query("UPDATE payslip SET overtimepay = :overtimepay where id = :id ")
    void upoverpay(@Param("overtimepay") Integer overtimepay,@Param("id") Integer id);
}
 