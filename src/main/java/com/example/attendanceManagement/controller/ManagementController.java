package com.example.attendanceManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.form.User_tableForm;
import com.example.attendanceManagement.method.ManagementMethod;
import com.example.attendanceManagement.service.User_tableService;

@Controller
@RequestMapping("/management")
public class ManagementController {
	
	@Autowired
	User_tableService service;
	ManagementMethod m = new ManagementMethod(); 
	
	@ModelAttribute
	public User_tableForm setUpForm() {
		User_tableForm form = new User_tableForm();
		return form;
	}
	
	@GetMapping	
	public String managementPage() {
		return "managementpage";
	}
	
	@GetMapping("/accountedit")
	public String accauntManagement() {
		
		return "accountmanagement";
	}
	
	//基本情報新規登録用メソッド
	@PostMapping("/insert")
	public String insert(@Validated User_tableForm user_tableForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
			User_table  u = new User_table();
			if(!bindingResult.hasErrors()) {
				redirectAttributes.addFlashAttribute("complete","登録が完了しました");
				return "redirect:/managementpage";
			}else {
				return null;
			}
	}
}
