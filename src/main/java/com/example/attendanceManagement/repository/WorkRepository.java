package com.example.attendanceManagement.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.attendanceManagement.entity.Work;
import com.example.demo.Param;

public interface WorkRepository extends CrudRepository<Work, Integer>{

	@Query("SELECT * FROM work WHERE approval is NULL")
	Iterable<Work> getWork();
	
	@Query("SELECT * FROM work WHERE id =:id and day = :day")
	Optional<Work> selectW(
			@Param("id") Integer id,
			@Param("day") Date day);
}