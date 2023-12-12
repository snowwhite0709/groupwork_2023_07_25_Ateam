package com.example.attendanceManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	// SecurityConfig の failureUrl で指定した URL と?のうしろのパラメータ
	@GetMapping(value = "/login", params = "failure")
	public String loginFail(Model model) {
		model.addAttribute("failureMessage", "ログインに失敗しました");
		// ログイン画面を表示
		return "login";
	}
//	// SecurityConfig の defaultSuccessUrl で指定した URL
//		@GetMapping("loginsuccess")
//		public String loginSuccess(Model model) {
//		// ユーザー名
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
//		model.addAttribute("username", principal.getUsername());
//		// ログインに成功したら表示する URL
//		return "success";
//		}
}
