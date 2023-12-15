package com.example.attendanceManagement.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.attendanceManagement.entity.Work;

public interface WorkRepository extends CrudRepository<Work, Integer>{

	@Query("SELECT * FROM work WHERE approval is NULL")
	Iterable<Work> getWork();
	
}
