package com.example.attendanceManagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.attendanceManagement.entity.Payslip;

public interface PayslipRepository extends CrudRepository<Payslip, Integer> {
    // 任意のクエリメソッドやカスタムメソッドを追加することができます
    // 例：List<PayslipEntity> findByFirstname(String firstname);
}
