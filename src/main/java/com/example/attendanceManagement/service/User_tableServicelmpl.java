package com.example.attendanceManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.repository.User_tableRepository;

@Service
@Transactional
public class User_tableServicelmpl implements User_tableService{
	
	@Autowired
	User_tableRepository repository;
	
	@Override
	public Iterable<User_table> SelectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findAll();
	}

	@Override
	public Optional<User_table> SlectOneById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findById(id);
	}

	@Override
	public void Insert(User_table user_table) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(user_table);
		
	}

	@Override
	public void Update(User_table user_table) {
		repository.save(user_table);
		
	}

	@Override
	public Iterable<User_table> getAll() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.getAll();
	}

	@Override
	public void sAll(User_table user_table) {
		repository.insertLoginUser(user_table.getPass(), user_table.getLastname(), user_table.getFirstname(), user_table.getSex(), user_table.getAge(), user_table.getStatus(), user_table.getRank(), user_table.getAdmin());
		
	}

}