package com.example.attendanceManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.attendanceManagement.entity.Shift_table;
import com.example.attendanceManagement.repository.Shift_tableRepository;

@Service
@Transactional
public class Shift_tableServiceImple implements Shift_tableService{
	@Autowired
	Shift_tableRepository repository;
	
	@Override
	public Iterable<Shift_table> SelectAll() {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findAll();
	}

	@Override
	public Optional<Shift_table> SlectOneById(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findById(id);
	}

	@Override
	public void Insert(Shift_table shift_table) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(shift_table);
	}

	@Override
	public void Update(Shift_table shift_table) {
		// TODO 自動生成されたメソッド・スタブ
		repository.save(shift_table);
	}

	@Override
	public void Shiftup(Shift_table shift_table) {
		// TODO 自動生成されたメソッド・スタブ
		repository.updateShiftTable(shift_table.getEmployee_id(),shift_table.getUsername(),shift_table.getYearmonth(),shift_table.getMonth(),shift_table.getWorkday(),shift_table.getVariableattendance(),shift_table.getKind(),shift_table.getApproval());
		
	}

	@Override
	public Iterable<Shift_table> idMonthAll(Integer employee_id,String month) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.idMonthAll(employee_id,month);
	}

	@Override
	public String idMonthUsernameSelect(Integer employee_id, String month) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.idMonthUsernameSelect(employee_id,month);
	}

	

}
