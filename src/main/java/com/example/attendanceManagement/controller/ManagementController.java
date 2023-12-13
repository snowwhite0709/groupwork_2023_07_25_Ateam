package com.example.attendanceManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementController {

	@GetMapping("management")
	public String managementPage() {
		
		return "managementpage";
	}
}
