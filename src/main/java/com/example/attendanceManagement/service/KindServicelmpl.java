package com.example.attendanceManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.attendanceManagement.entity.Kind;
import com.example.attendanceManagement.repository.KindRepository;

@Service
@Transactional
public class KindServicelmpl implements KindService{

	@Autowired
	KindRepository repository;
	
	@Override
	public Iterable<Kind> SelectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findAll();
	}

	@Override
	public Optional<Kind> SlectOneById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findById(id);
	}

	@Override
	public void Insert(Kind kind) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(kind);
	}

	@Override
	public void Update(Kind kind) {
		repository.save(kind);
	}
}
