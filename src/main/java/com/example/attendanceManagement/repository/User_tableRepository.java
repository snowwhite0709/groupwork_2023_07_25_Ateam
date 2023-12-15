package com.example.attendanceManagement.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.attendanceManagement.entity.User_table;

public interface User_tableRepository extends CrudRepository<User_table, Integer>{

	/*ID順に並べ替える*/
	@Query("SELECT * FROM user_table ORDER BY id ASC")
	Iterable<User_table> getAll();
	
}
