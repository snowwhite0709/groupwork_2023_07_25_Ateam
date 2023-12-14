package com.example.attendanceManagement.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainpageController {
	@GetMapping("/show")
	public String sample(Model model) {
		return "tanaka";
	}
	@GetMapping("/personal")
	public String personal() {
		return "";
	}
	@GetMapping("/attend")
	public String attend() {
		Date date=new Date();
		System.out.println(date);
		return "redirect:/show";
	}
	@GetMapping("/leave")
	public String leave() {
		Date date=new Date();
		System.out.println(date);
		return "redirect:/show";
	}
}	