package com.example.attendanceManagement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.service.UserDetailServiceImpl;
import com.example.attendanceManagement.service.WorkServicelmpl;

@Controller
public class dummyController {
	@Autowired
	WorkServicelmpl service;
	
	@GetMapping("/table")
	public String showTable(Model model, Authentication authentication) {
		
//		System.out.println("id: " + authentication.getPrincipal());
		
		System.out.println("id: " + UserDetailServiceImpl.USERID);
		
		List<String> todayWork = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String dbToDay;
		String toDay;
		
		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		
		String Kongetu = sdf2.format(new Date());
		
		//workテーブルの情報を取得
		Iterable<Work> work = service.SelectAll();
		//List型を宣言
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む
		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id()  == UserDetailServiceImpl.USERID && sdf2.format(w.getDay()).equals(Kongetu)) {
				//ListにEmpleyee_idが1の情報を追加
				list.add(w);
				//DBの年月日と今日の年月日が一緒であればtodayWorkに出勤時間と退勤時間を追加
				dbToDay = sdf.format(w.getDay());
				toDay = sdf.format(new Date());
				if(dbToDay.equals(toDay)){
					todayWork.add(w.getAttendancetime());
					todayWork.add(w.getLeavingtime());
				}
			}
			yearMonth.add(sdf2.format(w.getDay()));
		}
		System.out.println("list size : " + list.size());
		
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		//HTMLに送る
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		
		return "attendance-info";
	}
	
    @PostMapping("/submitForm")
    public String handleFormSubmission(@RequestParam("yearMonth") String selectedYearMonth,Model model) {
		
		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		
		
		//workテーブルの情報を取得
		Iterable<Work> work = service.SelectAll();
		//List型を宣言
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む
		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id() == 1 && sdf2.format(w.getDay()).equals(selectedYearMonth)) {
				//ListにEmpleyee_idが1の情報を追加
				list.add(w);
				//DBの年月日と今日の年月日が一緒であればtodayWorkに出勤時間と退勤時間を追加
			}
			yearMonth.add(sdf2.format(w.getDay()));
		}
		System.out.println("list size : " + list.size());
		
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		
		//HTMLに送る
		model.addAttribute("list", list);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("selectedYearMonth", selectedYearMonth);

        // 他の処理や遷移先を返す
        return "selectedYearMonthAttendance";
    }
	

}
