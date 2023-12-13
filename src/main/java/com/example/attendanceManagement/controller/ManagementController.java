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
		User_table user_table = new User_table();
		user_table.setPass(user_tableForm.getPass());
		user_table.setLastname(user_tableForm.getLastname());
		user_table.setFirstname(user_tableForm.getFirstname());
		user_table.setSex(user_tableForm.getSex());
		user_table.setAge(user_tableForm.getAge());
		user_table.setStatus(user_tableForm.getStatus());
		user_table.setRank(user_tableForm.getRank());
		user_table.setAdmin(user_tableForm.getAdmin());
		
		if(!bindingResult.hasErrors()) {
			service.Insert(user_table);
			redirectAttributes.addFlashAttribute("complete","登録が完了しました");
			return "redirect:/management/accountedit";
		}else {
			return null;
		}
	}
}
