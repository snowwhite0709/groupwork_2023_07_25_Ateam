package com.example.attendanceManagement.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.attendanceManagement.entity.Payslip;
import com.example.attendanceManagement.service.PayslipService;
import com.example.attendanceManagement.service.userdetails.UserDetailsImpl;

@Controller
@RequestMapping("/management")
public class PayslipController {

	@Autowired
	private PayslipService payslipService;

	@PostMapping("/pay")
	public String calculateSalary(@RequestParam("yearMonth") String selectedYearMonth,
			@RequestParam(name = "year", required = false) Integer year,
			@RequestParam(name = "month", required = false) Integer month,
			Model model, Authentication auth)  {
		System.out.println("ペイぺいぺい");
		/*現在のユーザー情報を取得*/
		// UserDetailsを取り出す
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		// ユーザーIDを取得
		Integer userId = userDetails.getUser_Id();
		model.addAttribute("userId",userId);
		
		String userName=userDetails.getUsername();
		model.addAttribute("username",userName);
		//System.out.println("ユーザーネーム"+userName);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		
		Iterable<Payslip> pay =payslipService.selectI(userId);
		Payslip paypay = new Payslip();
		for(Payslip p : pay) {
			if(sdf2.format(p.getDay()).equals(selectedYearMonth)) {
				System.out.println(p);
				paypay = p;
			}
		}
		model.addAttribute("selectedYearMonth", selectedYearMonth);
		model.addAttribute("paypay", paypay);
		return "payslip";
	}
	@GetMapping("/employee/pay")
	public String AAA(Model model, Authentication auth) {
		/*現在のユーザー情報を取得*/
		// UserDetailsを取り出す
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		// ユーザーIDを取得
		Integer userId = userDetails.getUser_Id();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		Iterable<Payslip> pay =payslipService.selectI(userId);
		List<String> yearMonth = new ArrayList<>();
		for(Payslip p : pay) {
			yearMonth.add(sdf2.format(p.getDay()));
		}
		model.addAttribute("yearMonth", yearMonth);
		
		return "abot";
	}

}