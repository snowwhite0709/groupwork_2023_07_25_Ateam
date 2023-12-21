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
import com.example.attendanceManagement.service.WorkServicelmpl;
import com.example.attendanceManagement.service.userdetails.UserDetailsImpl;

@Controller
public class dummyController {
	@Autowired
	WorkServicelmpl service;

	@GetMapping("/table")
	public String showTable(Model model, Authentication auth) {
		
		/*現在のユーザー情報を取得*/
		// UserDetailsを取り出す
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		// ユーザーIDを取得
		Integer userId = userDetails.getUser_Id();
		//System.out.println("UserDetailUserId"+userId);
		
		//DBのDate型の日付をString型にして比較したりする用フォーマット
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		//DB上の日付
		String dbToDay;
		//今日の日付
		String toDay;
		//当日の出退勤の時刻をHTMLに送るためのList
		List<String> todayWork = new ArrayList<>();
		//ログイン中のIDの出退勤があった年月を保存するSet
		Set<String>yearMonth = new TreeSet<>();
		//今月の年月String型を取得
		String thisMonth = sdf2.format(new Date());
		//ログイン中のIDのworkテーブルの情報を取得
		Iterable<Work> work = service.selectI(userId);
		//指定した月の出退勤情報をHTMLに送るためのList
		List<Work> list = new ArrayList<>();

		//残業時間（HH）計算用
		int sumHours = 0;
		//残業時間(mm)計算用
		int sumMinutes = 0;
		//Listに要素を詰め込む（一旦全件取得）
		for(Work w : work) {
			//ログイン中のIDの出退勤があった年月を保存
			yearMonth.add(sdf2.format(w.getDay()));
			//今月の情報をリストに追加
			if(sdf2.format(w.getDay()).equals(thisMonth)) {
				//勤務時間
				list.add(w);
				//残業時間がnullの時は何もしない
				if(w.getOvertime() != null) {
					String[] overTime;
					//残業時間をMMとmmに分割する
					overTime = w.getOvertime().split(":");
					//int型に変換して足していく
					sumHours += Integer.parseInt(overTime[0]);
					sumMinutes += Integer.parseInt(overTime[1]);
				}
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
		//月の残業時間（mm)を60で割って、割り切れた数を月の残業時間(HH)に足す
		//割り切れない分は月の残業時間(mm）になります。
		sumHours += sumMinutes/60;
		sumMinutes = sumMinutes%60;
		//System.out.println("sumHours:"+sumHours);
		//System.out.println("sumMinutes:"+sumMinutes);
		String fmt = "%02d:%02d";
		String totalOverTime = String.format(fmt, sumHours, sumMinutes);
		//昇順に並び替え
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		//HTMLに送る
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("totalOverTime", totalOverTime);

		return "attendance-info";
	}

	@PostMapping("/submitForm")
	public String handleFormSubmission(@RequestParam("yearMonth") String selectedYearMonth,Model model, Authentication auth) {
		
		/*現在のユーザー情報を取得*/
		// UserDetailsを取り出す
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		// ユーザーIDを取得
		Integer userId = userDetails.getUser_Id();
		//System.out.println("UserDetailUserId"+userId);
		
		//ログイン中のIDの出退勤があった年月を保存するSet
		Set<String>yearMonth = new TreeSet<>();
		//DBのDate型の日付をString型にして比較したりする用フォーマット
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		//ログイン中のIDのworkテーブルの情報を取得
		Iterable<Work> work = service.selectI(userId);
		//指定した月の出退勤情報をHTMLに送るためのList
		List<Work> list = new ArrayList<>();

		//残業時間（HH）計算用
		int sumHours = 0;
		//残業時間(mm)計算用
		int sumMinutes = 0;

		//Listに要素を詰め込む
		for(Work w : work) {
			yearMonth.add(sdf2.format(w.getDay()));
			if(sdf2.format(w.getDay()).equals(selectedYearMonth)) {
				list.add(w);
				if(w.getOvertime() != null) {
					//残業時間をMMとmmに分割する
					String[] overTime;
					overTime = w.getOvertime().split(":");
					//int型に変換して足していく
					sumHours += Integer.parseInt(overTime[0]);
					sumMinutes += Integer.parseInt(overTime[1]);
				}
			}
		}

		sumHours += sumMinutes/60;
		sumMinutes = sumMinutes%60;
		//System.out.println("sumHours:"+sumHours);
		//System.out.println("sumMinutes:"+sumMinutes);
		String fmt = "%02d:%02d";
		String totalOverTime = String.format(fmt, sumHours, sumMinutes);
		//昇順に並び替え
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));

		//HTMLに送る
		model.addAttribute("list", list);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("selectedYearMonth", selectedYearMonth);
		model.addAttribute("totalOverTime", totalOverTime);

		// 他の処理や遷移先を返す
		return "selectedYearMonthAttendance";
	}




}
