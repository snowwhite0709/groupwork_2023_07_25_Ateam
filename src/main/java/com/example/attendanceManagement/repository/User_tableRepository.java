package com.example.attendanceManagement.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.attendanceManagement.entity.User_table;

public interface User_tableRepository extends CrudRepository<User_table, Integer>{

	/*ID順に並べ替える*/
	@Query("SELECT * FROM user_table ORDER BY id ASC")
	Iterable<User_table> getAll();
	
	@Modifying
    @Query("INSERT INTO login_user (username, password, role_id) VALUES (:username, :password, :roleId)")
    void insertLoginUser(@Param("username") String username, @Param("password") String password, @Param("roleId") Integer roleId);
}
