package com.example.attendanceManagement.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.attendanceManagement.entity.Paypay;
import com.example.attendanceManagement.repository.PaypayRepository;
@Service
@Transactional
public class PaypayServicelmpl implements PaypayService{

	@Autowired
	PaypayRepository repository;

	@Override
	public Iterable<Paypay> SelectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findAll();
	}

	@Override
	public Optional<Paypay> SlectOneById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findById(id);
	}

	@Override
	public void Insert(Paypay paypay) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(paypay);
	}

	@Override
	public void Update(Paypay paypay) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(paypay);
	}

	@Override
	public void save(Integer i,Integer b,Date d) {
		// TODO 自動生成されたメソッド・スタブ
		repository.inpay(i, b, d);
	}

	@Override
	public Integer selectBP(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.selectBP(id);
	}


}
