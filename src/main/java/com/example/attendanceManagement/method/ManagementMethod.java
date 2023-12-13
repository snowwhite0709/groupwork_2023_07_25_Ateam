package com.example.attendanceManagement.method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.attendanceManagement.controller.ManagementController;
import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.form.User_tableForm;
import com.example.attendanceManagement.service.User_tableService;

public class ManagementMethod {
	@Autowired
	User_tableService service; 
	
	public String dataSet(int id,User_tableService service,Model model) {
		Iterable<User_table> list = service.SelectAll();
		//HTMLに値を渡す
		model.addAttribute("list",list);

		return "home";

	}
	
	//基本情報用
		public String regist(@Validated User_tableForm sampleForm, BindingResult bindingResult,
				Model model, RedirectAttributes redirectAttributes, User_tableService service) {
			User_table sample = new User_table();
			
			ManagementController m = new ManagementController();
			if(!bindingResult.hasErrors()) {
				service.Insert(sample);
				redirectAttributes.addFlashAttribute("complete","登録が完了しました");
				return "redirect:/home";
			}else {
				return null;
			}

		}
}
