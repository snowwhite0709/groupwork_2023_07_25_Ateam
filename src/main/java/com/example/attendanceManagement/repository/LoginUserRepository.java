package com.example.attendanceManagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.attendanceManagement.entity.LoginUser;

public interface LoginUserRepository extends CrudRepository<LoginUser, String> {
}

