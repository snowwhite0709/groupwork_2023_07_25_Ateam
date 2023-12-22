package com.example.attendanceManagement.method;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.ui.Model;

import com.example.attendanceManagement.entity.Payslip;
import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.form.PaypayForm;
import com.example.attendanceManagement.service.PaypayService;
import com.example.attendanceManagement.service.PayslipService;
import com.example.attendanceManagement.service.WorkService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GetIdMethod {
	
	static int id;
	static String thisMonth;

	public GetIdMethod(int id ) {
		GetIdMethod.id = id;
	}
	
	//当月の勤怠表示
	public void getMonth(Model model,WorkService workService,PayslipService payslipService,PaypayService paypayService) {
		List<String> todayWork = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		String dbToDay;
		String toDay;

		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		//今月の年月を取得
		String Kongetu = sdf2.format(new Date());
		thisMonth = Kongetu;
		//workテーブルの情報を取得
		Iterable<Work> work = workService.selectI(id);

		//List型を宣言
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む

		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id()  == id && sdf2.format(w.getDay()).equals(Kongetu)) {
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
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		
		//HTMLに送る
		showPayslip(payslipService, Kongetu, model,paypayService);
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("thisMonth",Kongetu);
	}
	
	//月を選択した際の表示
	public void getNowMonth(Model model,WorkService workService,String selectedYearMonth,PayslipService payslipService,PaypayService paypayService) {
		List<String> todayWork = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String dbToDay;
		String toDay;
		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");

		//workテーブルの情報を取得
		Iterable<Work> work = workService.selectI(id);

		//List型を宣言
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む
		thisMonth = selectedYearMonth;
		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id()  == id && sdf2.format(w.getDay()).equals(selectedYearMonth)) {
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
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		//HTMLに送る
		showPayslip(payslipService, selectedYearMonth, model,paypayService);
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("thisMonth",selectedYearMonth);
	}
	
	//給与表示用メソッド
	public void showPayslip(PayslipService payslipService,String Kongetu,Model model,PaypayService paypayService) {
		Iterable<Payslip> pay = payslipService.selectI(id);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		Integer i = 0;
		for(Payslip p :pay) {
			if (sdf2.format(p.getDay()).equals(Kongetu)) {
				i = p.getBasepay();
			}
		}
		if(i == 0) {
			i = paypayService.selectBP(id);
		}
		model.addAttribute("plist",i);
	}
	
	//基本給登録
	public void setpaypay(PaypayService paypayService,PaypayForm paypayForm) {
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
		paypayService.save(id,paypayForm.getBasepay(),sqlDate);
	}
}

