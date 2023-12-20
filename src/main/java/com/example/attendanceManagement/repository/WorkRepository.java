package com.example.attendanceManagement.repository;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.attendanceManagement.entity.Work;

public interface WorkRepository extends CrudRepository<Work, Integer>{

	@Query("SELECT * FROM work WHERE approval is NULL")
	Iterable<Work> getWork();
	
	@Query("SELECT * FROM work WHERE employee_id=:employee_id and day = :day")
	Optional<Work> selectW(@Param("employee_id") Integer employee_id,@Param("day") Date day);
	
	@Query("SELECT * FROM work WHERE employee_id=:employee_id")
	Optional<Work> selectW2(
			@Param("employee_id") Integer employee_id);
}
