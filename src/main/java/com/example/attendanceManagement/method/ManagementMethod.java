package com.example.attendanceManagement.method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.form.User_tableForm;
import com.example.attendanceManagement.service.User_tableService;

public class ManagementMethod {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String show(@Validated User_tableForm user_tableForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes,User_tableService user_tableService) {
		User_table user_table = new User_table();

		user_table.setPass(passwordEncoder.encode(user_tableForm.getPass()));
		user_table.setLastname(user_tableForm.getLastname());
		user_table.setFirstname(user_tableForm.getFirstname());
		user_table.setSex(user_tableForm.getSex());
		user_table.setAge(user_tableForm.getAge());
		user_table.setStatus(user_tableForm.getStatus());
		user_table.setRank(user_tableForm.getRank());
		user_table.setAdmin(user_tableForm.getAdmin());


		if(!bindingResult.hasErrors()) {
			user_tableService.sAll(user_table);
			redirectAttributes.addFlashAttribute("complete","登録が完了しました");
			return "redirect:/management/accountedit";
		}else {
			return "accountmake";
		}
	}


}
