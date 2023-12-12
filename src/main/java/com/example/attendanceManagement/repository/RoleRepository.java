package com.example.attendanceManagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.attendanceManagement.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
