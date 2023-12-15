package com.example.attendanceManagement.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Admin {
	@Id
	private Integer id;
	private String name;
}
