package com.example.attendanceManagement.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.repository.WorkRepository;

@Service
@Transactional
public class WorkServicelmpl implements WorkService{

	@Autowired
	WorkRepository repository;
	
	@Override
	public Iterable<Work> SelectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findAll();
	}

	@Override
	public Optional<Work> SlectOneById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findById(id);
	}

	@Override
	public void InsertWork(Work work) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(work);
		
	}

	@Override
	public void UpdateWork(Work work) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(work);
	}

	@Override
	public Iterable<Work> getWork() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.getWork();
	}

	@Override
	public Optional<Work> selectW(Integer id, Date day) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.selectW(id, day);
	}

}