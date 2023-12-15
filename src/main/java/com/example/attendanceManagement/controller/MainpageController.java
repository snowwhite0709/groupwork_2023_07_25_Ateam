package com.example.attendanceManagement.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.entity.beforeWork;
import com.example.attendanceManagement.service.WorkService;

@Controller
public class MainpageController {
	@Autowired
	WorkService workService;
	
	@GetMapping("/show")
	public String sample(Model model) {
		return "tanaka";
	}
	@GetMapping("/personal")
	public String personal() {
		return "/table";
	}
	@GetMapping("/attend")
	public String attend() {
		Date date1=new Date();
		SimpleDateFormat f=new SimpleDateFormat("HH:mm");
		String a=f.format(date1);
		beforeWork bw=new beforeWork(1,date1);
		java.sql.Date sqlDate=new java.sql.Date(date1.getTime());
		Work work=new Work(1,1,sqlDate,a,null,null,null,null);
		workService.InsertWork(work);
		return "redirect:/show";
	}
	@GetMapping("/leave")
	public String leave() {
		Date date2=new Date();
		SimpleDateFormat f=new SimpleDateFormat("HH:mm");
		SimpleDateFormat ff=new SimpleDateFormat("HH");
		String a=f.format(date2);
		beforeWork bw=new beforeWork(1,date2);
		java.sql.Date sqlDate=new java.sql.Date(date2.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.HOUR, -9);
		Date date3 = calendar.getTime();
		String b=ff.format(date3);
		int i=Integer.parseInt(b);
		if(i>9) {
			calendar.add(Calendar.HOUR, -9);
			Date date4 = calendar.getTime();
			String c=f.format(date4);
			Work work=new Work(1,1,sqlDate,null,a,c,null,null);
		}else {
			Work work=new Work(1,1,sqlDate,null,a,null,null,null);
		}
		workService.InsertWork(work);
		return "redirect:/show";
	}
}	