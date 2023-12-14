package com.example.attendanceManagement.controller;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.form.User_tableForm;
import com.example.attendanceManagement.service.User_tableService;
import com.example.attendanceManagement.service.WorkService;

@Controller
@RequestMapping("/management")
public class ManagementController {
	
	@Autowired
	User_tableService user_tableService;
	@Autowired
	WorkService workService;
	
	@ModelAttribute
	public User_tableForm setUpForm() {
		User_tableForm form = new User_tableForm();
		return form;
	}
	
	//トップページ（勤怠承認画面）へ遷移するためのメソッド
	@GetMapping	
	public String managementPage(Model model) {
		Iterable<Work> list = workService.getWork();
		model.addAttribute("list",list);
		return "managementpage";
	}
	
	//アカウント管理画面へ遷移するためのメソッド
	@GetMapping("/accountmanagement")
	public String accountmanagement() {
		return "accountmanagement";
	}
	
	//アカウント新規作成画面へ遷移するためのメソッド
	@GetMapping("/accountedit")
	public String accauntManagement() {
		return "accountmake";
	}
	
	//アカウント新規作成の情報登録用メソッド
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
			user_tableService.Insert(user_table);
			redirectAttributes.addFlashAttribute("complete","登録が完了しました");
			return "redirect:/management/accountedit";
		}else {
			return null;
		}
	}
	
	//トップページで、未承認の勤怠情報を承認するためのメソッド
	@PostMapping("/{id}/{day}/{attendancetime}/{leavingtime}")
	public String approval(@PathVariable Integer id, @PathVariable Timestamp day, @PathVariable Timestamp attendancetime, @PathVariable Timestamp leavingtime) {
			Work work = new Work();
			System.out.println(id);
			System.out.println(day);
			System.out.println(attendancetime);
			System.out.println(day);
			Optional<Work> w = workService.getOneWork(id, day, attendancetime, leavingtime);
			work = w.get();
			work.setApproval(true);
			workService.UpdateWork(work);
		return "managementpage";
	}
}
