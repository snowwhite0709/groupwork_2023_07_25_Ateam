package com.example.attendanceManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class ManagementController {

	@GetMapping
	public String managementPage() {
		
		return "managementpage";
	}
	
	@GetMapping("/accountedit")
	public String accauntManagement() {
		
		return "accountmanagement";
	}
}
