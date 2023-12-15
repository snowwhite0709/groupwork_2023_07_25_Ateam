package com.example.attendanceManagement.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.attendanceManagement.entity.Work;
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
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.clear(Calendar.MINUTE);
	    calendar.clear(Calendar.SECOND);
	    calendar.clear(Calendar.MILLISECOND);
	    // 時の部分をクリアするには、setで入れないといけない。
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    Date date2 = calendar.getTime();
		java.sql.Date sqlDate=new java.sql.Date(date2.getTime());
		Optional<Work> o=workService.SlectOneById(1);
		Work w = o.get();
		w.setDay(sqlDate);
		w.setAttendancetime(a);
		workService.InsertWork(w);
		return "redirect:/show";
	}
	@GetMapping("/leave")
	public String leave() {
		Date date2=new Date();
		SimpleDateFormat f=new SimpleDateFormat("HH:mm");
		SimpleDateFormat ff=new SimpleDateFormat("HH");
		String a=f.format(date2);
		//beforeWork bw=new beforeWork(1,date2);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date2);
		calendar1.clear(Calendar.MINUTE);
	    calendar1.clear(Calendar.SECOND);
	    calendar1.clear(Calendar.MILLISECOND);
	    // 時の部分をクリアするには、setで入れないといけない。
	    calendar1.set(Calendar.HOUR_OF_DAY, 0);
	    Date d = calendar1.getTime();
		
		//java.sql.Date sqlDate=new java.sql.Date(date2.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.HOUR, -9);
		Date date3 = calendar.getTime();
		String b=ff.format(date3);
		int i=Integer.parseInt(b);
		Optional<Work> o=workService.selectW(1,d);
		Work w = o.get();
		if(i>9) {
			calendar.add(Calendar.HOUR, -9);
			Date date4 = calendar.getTime();
			String c=f.format(date4);
			w.setLeavingtime(a);
			w.setOvertime(c);
		}else {
			w.setLeavingtime(a);
		}
		workService.InsertWork(w);
		return "redirect:/show";
	}
}	