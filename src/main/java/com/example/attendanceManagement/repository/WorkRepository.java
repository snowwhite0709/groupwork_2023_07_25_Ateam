package com.example.attendanceManagement.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.attendanceManagement.entity.Work;

public interface WorkRepository extends CrudRepository<Work, Integer>{

	@Query("SELECT * FROM work WHERE approval is NULL")
	Iterable<Work> getWork();
	
	@Query("SELECT * FROM work WHERE id =:id and day = :day and attendancetime = :attendancetime and leavingtime = :leavingtime")
	Optional<Work> getOneWork(
			@Param("id") Integer id,
			@Param("day") Timestamp day, 
		    @Param("attendancetime") Timestamp attendancetime,
		    @Param("leavingtime") Timestamp leavingtime);
	
	@Query("SELECT employees FROM registory WHERE id = :employees")
	Integer getEmployees(@Param("employees") Integer id);
}
