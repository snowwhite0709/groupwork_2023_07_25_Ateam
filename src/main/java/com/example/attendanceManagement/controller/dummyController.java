package com.example.attendanceManagement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String showTable(Model model) {
		System.out.println("id: " + UserDetailServiceImpl.USERID);
		//DBのDate型の日付をString型にして比較したりする用フォーマット
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		//DB上の日付
		String dbToDay;
		//今日の日付
		String toDay;
		//当日の出退勤の時刻をHTMLに送るためのList
		List<String> todayWork = new ArrayList<>();
		//指定したemployee_idの出退勤があった年月を保存するSet
		Set<String>yearMonth = new TreeSet<>();
		//今月の年月String型を取得
		String Kongetu = sdf2.format(new Date());
		//workテーブルの情報を取得
		Iterable<Work> work = service.SelectAll();
		//指定した月の出退勤情報をHTMLに送るためのList
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む（一旦全件取得）
		for(Work w : work) {
			//user_tableのIDとログイン中のIDを比較
			if (w.getEmployee_id()  == UserDetailServiceImpl.USERID) {
				//指定したemployee_idの出退勤があった年月を保存
				yearMonth.add(sdf2.format(w.getDay()));
				//今月の情報をリストに追加
				if(sdf2.format(w.getDay()).equals(Kongetu)) {
					list.add(w);
					//DB上のDate型の数値を年月日のString型に変更
					dbToDay = sdf.format(w.getDay());
					//当日の年月日をString型で取得
					toDay = sdf.format(new Date());
					//DB上の日付と当日の日付を比較、一致すれば出退勤情報をリストに追加
					if(dbToDay.equals(toDay)){
						todayWork.add(w.getAttendancetime());
						todayWork.add(w.getLeavingtime());
					}
				}	
			}
		}
		//昇順に並び替え
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
		//指定した月の出退勤情報をHTMLに送るためのList
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む
		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id() == UserDetailServiceImpl.USERID && sdf2.format(w.getDay()).equals(selectedYearMonth)) {
				list.add(w);
				yearMonth.add(sdf2.format(w.getDay()));
			}
		}
		//昇順に並び替え
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
