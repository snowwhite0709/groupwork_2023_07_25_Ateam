package com.example.attendanceManagement.repository;

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

}
 