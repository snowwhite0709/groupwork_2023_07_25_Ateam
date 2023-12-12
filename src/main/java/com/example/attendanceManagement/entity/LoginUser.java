package com.example.attendanceManagement.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUser {
@Id
private String username;
private String password;
private Integer roleId;
}