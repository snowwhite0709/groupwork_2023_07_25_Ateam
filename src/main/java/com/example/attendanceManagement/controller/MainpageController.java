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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@PostMapping("/personal")
	public String personal(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
//			Work work = new Work();
//			Optional<Work> w = workService.SlectOneById(id);
//			work = w.get();
//			work.setApproval(true);
//			workService.UpdateWork(work);
			redirectAttributes.addFlashAttribute("user_ui",id);
		
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
		System.out.println("sql:"+sqlDate);
		//Optional<Work> o=workService.SlectOneById(1);
		
		
		Work w=new Work();
		w.setEmployee_id(UserDetailServiceImpl.USERID);
		w.setDay(sqlDate);
		w.setAttendancetime(a);
		System.out.println("w:"+w);
		
		Optional<Work> o=workService.selectW(UserDetailServiceImpl.USERID,sqlDate);
		System.out.println("o:"+o);
		//Work ww = o.get();
		//System.out.println("ww:"+ww);
		
		if(o.isEmpty()) {
			workService.InsertWork(w);
		}
		return "redirect:/show";
	}
	
	@GetMapping("/leave")
	public String leave(Authentication auth) {
		System.out.println("auth: " + auth.getName());
		System.out.println("auth: " + auth.getAuthorities());
		
		
		
	
		Date date2=new Date();
		System.out.println("date2:"+date2);
		
		SimpleDateFormat f=new SimpleDateFormat("HH:mm");
		SimpleDateFormat ff=new SimpleDateFormat("HH");
		SimpleDateFormat fff=new SimpleDateFormat("mm");
		
		String a=f.format(date2);
		System.out.println("a:"+a);
		
		//beforeWork bw=new beforeWork(1,date2);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date2);
		calendar1.clear(Calendar.MINUTE);
	    calendar1.clear(Calendar.SECOND);
	    calendar1.clear(Calendar.MILLISECOND);
	    // 時の部分をクリアするには、setで入れないといけない。
	    //calendar1.set(Calendar.HOUR_OF_DAY, 0);
	    Date d = calendar1.getTime();
		System.out.println("d:"+d);
	    
		java.sql.Date sqlDate=new java.sql.Date(d.getTime());
		System.out.println("sqlDate:"+sqlDate);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.HOUR, -9);
		Date date3 = calendar.getTime();
		System.out.println("date3:"+date3);
		
		String b=ff.format(date3);
		String bb=fff.format(date3);
		
		int i=Integer.parseInt(b);
		int ii=Integer.parseInt(bb);
		
		
		Optional<Work> o=workService.selectW(UserDetailServiceImpl.USERID,sqlDate);
		Work w = o.get();
		
		if(i>9 || (i==9&&ii>0)) {
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