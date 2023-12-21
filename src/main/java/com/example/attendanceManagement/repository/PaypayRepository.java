package com.example.attendanceManagement.repository;

import java.sql.Date;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.attendanceManagement.entity.Paypay;

public interface PaypayRepository extends CrudRepository<Paypay, Integer>{
	@Modifying
	@Query("INSERT INTO paypay (employee_id, basepay, day) "
    		+ "VALUES (:employee_id, :basepay, :day)")
    void inpay(@Param("employee_id") Integer employee_id, @Param("basepay") Integer basepay, 
    		@Param("day") Date day);

	
}
