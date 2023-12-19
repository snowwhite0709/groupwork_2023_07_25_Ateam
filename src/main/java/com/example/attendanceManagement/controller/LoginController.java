package com.example.attendanceManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.service.WorkService;
import com.example.attendanceManagement.service.userdetails.UserDetailsImpl;

@Controller
public class LoginController {
	
	@Autowired
	WorkService workService;
	
	@GetMapping("/login")
	public String showLogin(Model model) {
		
		return "login";
	}

	// SecurityConfig の failureUrl で指定した URL と?のうしろのパラメータ
	@GetMapping(value = "/login", params = "failure")
	public String loginFail(Model model) {
		model.addAttribute("failureMessage", "ログインに失敗しました");
		// ログイン画面を表示
		return "login";
	}
	
	
	
	// SecurityConfig の defaultSuccessUrl で指定した URL
	//admin権限を持ってページ移行
		@GetMapping("/management/admin")
		public String loginAdmin(Model model) {
			// ユーザー名
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
			model.addAttribute("username", principal.getUsername());
			
			// ログインに成功したら表示する URL
			return "managementpage";
		}
		//manager権限を持ってページ移行
		@GetMapping("/management/manager")
		public String loginManager(Model model) {
			// ユーザー名
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
//			model.addAttribute("username", principal.getUsername());
			Iterable<Work> list = workService.getWork();
			model.addAttribute("list",list);
			// ログインに成功したら表示する URL
			return "managementpage";
		}
		//employee権限を持ってページ移行
		@GetMapping("/tanaka/employee")
		public String loginEmployee(Model model) {
			// ユーザー名
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
			model.addAttribute("username", principal.getUsername());
			model.addAttribute("password", principal.getPassword());
			model.addAttribute("id", principal.getUser_Id());
			// ログインに成功したら表示する URL
			return "tanaka";
		}
		@GetMapping("/defaultRedirect")
		public String defaultRedirect(Authentication authentication) {
			if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
				return "redirect:/management/admin";
			} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"))) {
				return "redirect:/management/manager";
			} else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
				return "redirect:/tanaka/employee";
			}

			// handle the case when no matching role is found
			return "redirect:/error";
		}
		
		
//		@GetMapping("/admin")
//		public String admin() {
//			return "admin";
//		}
//		@PostMapping("/admin")
//		public void adminPost() {
//			admin();
//		}
//		@GetMapping("/manager")
//		public String manager() {
//			return "manager";
//		}
//		@PostMapping("/manager")
//		public void managerPost() {
//			manager();
//		}
//		@GetMapping("/employee")
//		public String employee() {
//			return "employee";
//		}
//		@PostMapping("/employee")
//		public void employeePost() {
//			employee();
//		}
}
