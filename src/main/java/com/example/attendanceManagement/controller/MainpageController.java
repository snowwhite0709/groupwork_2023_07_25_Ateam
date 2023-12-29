package com.example.attendanceManagement.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.service.UserDetailServiceImpl;
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
		return "/employee/table";
	}
	@GetMapping("/attend")
	public String attend() {
		Date date=new Date();
		SimpleDateFormat f=new SimpleDateFormat("HH:mm");
		String a=f.format(date);
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		Work w=new Work();
		w.setEmployee_id(UserDetailServiceImpl.USERID);
		w.setDay(sqlDate);
		w.setAttendancetime(a);
		Optional<Work> o=workService.selectW(UserDetailServiceImpl.USERID,sqlDate);		
		if(o.isEmpty()) {
			workService.InsertWork(w);
		}
		return "redirect:/show";
	}
	
	@GetMapping("/leave")
	public String leave(Authentication auth) {
		Date date=new Date();
		SimpleDateFormat f=new SimpleDateFormat("HH:mm");
		SimpleDateFormat ff=new SimpleDateFormat("HH");
		SimpleDateFormat fff=new SimpleDateFormat("mm");
		String a=f.format(date);
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());


		//チェック用
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, 2023);
		ca.set(Calendar.MONTH, Calendar.DECEMBER);
		ca.set(Calendar.DAY_OF_MONTH, 28);
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 30);
		ca.set(Calendar.SECOND, 0);
		Date specifiedDate = ca.getTime();
		
		
		String aa=f.format(specifiedDate);
		String b=ff.format(specifiedDate);
		String bb=fff.format(specifiedDate);
		int i=Integer.parseInt(b);
		int ii=Integer.parseInt(bb);
		/*String b=ff.format(date);
		String bb=fff.format(date);
		int i=Integer.parseInt(b);
		int ii=Integer.parseInt(bb);*/

		
		Optional<Work> o=workService.selectW(UserDetailServiceImpl.USERID,sqlDate);
		Work w = o.get();
		if(i>18 || (i==18&&ii>0)) {
			Calendar calendar = Calendar.getInstance();
			
			//チェック用
			calendar.setTime(specifiedDate);
			/*calendar.setTime(date);*/
			
			
			calendar.add(Calendar.HOUR, -18);
			Date date1 = calendar.getTime();
			String c=f.format(date1);
			
			//チェック用
			w.setLeavingtime(aa);
			/*w.setLeavingtime(a);*/
			
			
			w.setOvertime(c);
		}else {
			w.setLeavingtime(a);
		}
		workService.InsertWork(w);
		return "redirect:/show";
	}
}