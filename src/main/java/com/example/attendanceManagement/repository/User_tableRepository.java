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
    @Query("INSERT INTO login_user (pass, lastname, firstname, sex, age, status, rank, admin) "
    		+ "VALUES (:pass, :lastname, :firstname, :sex, :age, :status, :rank, :admin)")
    void insertLoginUser(@Param("pass") String pass, @Param("lastname") String lastname, @Param("firstname") String firstname,
    		@Param("sex") Integer sex, @Param("age") Integer age,@Param("status") Integer status,
    		 @Param("rank") Integer rank,@Param("admin") Integer admin);
	
	@Query("SELECT * FROM work WHERE approval is NULL")
	Iterable<User_table> getUser_table();
}


/*旧記載
@Modifying
@Query("INSERT INTO login_user (username, password, role_id) VALUES (:username, :password, :roleId)")
void insertLoginUser(@Param("username") String username, @Param("password") String password, @Param("roleId") Integer roleId);*/