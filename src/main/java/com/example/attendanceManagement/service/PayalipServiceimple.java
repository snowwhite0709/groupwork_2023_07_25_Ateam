package com.example.attendanceManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.attendanceManagement.entity.Payslip;
import com.example.attendanceManagement.repository.PayslipRepository;

@Service
@Transactional
public class PayalipServiceimple implements PayslipService{

	@Autowired
	PayslipRepository repository;
	
	@Override
	public Iterable<Payslip> SelectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Optional<Payslip> SlectOneById(Integer id) {
		Optional<Payslip> pay = repository.findById(id);
		
		// TODO 自動生成されたメソッド・スタブ
		return pay;
	}

	@Override
	public void Insert(Payslip payslip) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(payslip);
	}

	@Override
	public void Update(Payslip payslip) {
		// TODO 自動生成されたメソッド・スタブ
		 repository.save(payslip);
	}
	

}
