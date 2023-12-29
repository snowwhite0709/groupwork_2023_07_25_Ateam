package com.example.attendanceManagement.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.attendanceManagement.entity.Shift_table;

public interface Shift_tableRepository extends CrudRepository<Shift_table,Integer>{
 
	
	@Modifying
    @Query("INSERT INTO shift_table (employee_id, username, yearmonth,workday,variableattendance,kind,approval,month) "
    		+ "VALUES (:employee_id, :username, :yearmonth, :workday, :variableattendance, :kind, :approval, :month)")
	void insertUserShift(@Param("employee_id")Integer employee_id, @Param("username")String username, @Param("yearmonth")String yearmonth, @Param("workday")Boolean workday, 
			@Param("variableattendance")Integer variableattendance, @Param("kind")Integer kind, @Param("approval") Boolean approval, @Param("month")String month);
	
	@Modifying
    @Query("UPDATE shift_table SET employee_id = ;employee_id, username =  :username, yearmonth = :yearmonth, month = :month, workday = :workday, "
    		+ "variableattendance = :variableattendance, kind = :kind, approval = :approval WHERE employee_id = :employee_id AND yearmonth = :yearmonth) "
    		+ "VALUES (:employee_id, :username, :yearmonth, :month, :variableattendance, :kind, :approval)")
    void updateShiftTable(@Param("employee_id") Integer employee_id, @Param("username") String username, @Param("yearmonth") String yearmonth,
    		@Param("month") String month, @Param("workday") Boolean workday,@Param("variableattendance") Integer variableattendance,
    		 @Param("kind") Integer kind,@Param("approval") Boolean approval);
	
	@Query("SELECT * FROM Shift_table  WHERE employee_id = :employee_id AND month = :month ")
	Iterable<Shift_table> idMonthAll(@Param("employee_id") Integer employee_id, @Param("month") String month);
	
	@Query("SELECT distinct(username) FROM Shift_table  WHERE employee_id = :employee_id AND month = :month ")
	String idMonthUsernameSelect(@Param("employee_id") Integer employee_id, @Param("month") String month);
	
	
	
}
