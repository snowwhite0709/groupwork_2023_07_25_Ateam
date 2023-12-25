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
import com.example.attendanceManagement.form.PayslipForm;
import com.example.attendanceManagement.service.PaypayService;
import com.example.attendanceManagement.service.PayslipService;
import com.example.attendanceManagement.service.WorkService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GetIdMethod {
	
	//選択したid
	static int id;
	//当月または選択した月
	static String thisMonth;
	//当月または選択した月の残業時間
	static String thisOver;
	
	public GetIdMethod(int id ) {
		GetIdMethod.id = id;
	}
	
	//当月の勤怠表示
	public Integer getMonth(Model model,WorkService workService,PayslipService payslipService,PaypayService paypayService) {
		List<String> todayWork = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		//残業時間（HH）計算用
				int sumHours = 0;
				//残業時間(mm)計算用
				int sumMinutes = 0;

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
			if (w.getEmployee_id()  == id && sdf2.format(w.getDay()).equals(Kongetu) && (w.isApproval() == true)) {
				//ListにEmpleyee_idが1の情報を追加
				list.add(w);
				//DBの年月日と今日の年月日が一緒であればtodayWorkに出勤時間と退勤時間を追加
				dbToDay = sdf.format(w.getDay());
				toDay = sdf.format(new Date());
				if(dbToDay.equals(toDay)){
					todayWork.add(w.getAttendancetime());
					todayWork.add(w.getLeavingtime());
				}
				if(w.getOvertime() != null) {
					String[] overTime;
					//残業時間をMMとmmに分割する
					overTime = w.getOvertime().split(":");
					//int型に変換して足していく
					sumHours += Integer.parseInt(overTime[0]);
					sumMinutes += Integer.parseInt(overTime[1]);
				}
			}
			yearMonth.add(sdf2.format(w.getDay()));
		}		
		//残業代計算
		sumHours += sumMinutes/60;
		sumMinutes = sumMinutes%60;
		String fmt = "%02d:%02d";
		thisOver = String.format(fmt, sumHours, sumMinutes);
		
		
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		
		//HTMLに送る
		showPayslip(payslipService, Kongetu, model,paypayService);
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("thisMonth",Kongetu);
		model.addAttribute("totalOverTime",thisOver);
		
		return id;
	}
	
	//月を選択した際の表示
	public Integer getNowMonth(Model model,WorkService workService,String selectedYearMonth,PayslipService payslipService,PaypayService paypayService) {
		List<String> todayWork = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String dbToDay;
		String toDay;
		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		
		//残業時間（HH）計算用
		int sumHours = 0;
		//残業時間(mm)計算用
		int sumMinutes = 0;

		//workテーブルの情報を取得
		Iterable<Work> work = workService.selectI(id);

		//List型を宣言
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む
		thisMonth = selectedYearMonth;
		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id()  == id && sdf2.format(w.getDay()).equals(selectedYearMonth) && (w.isApproval() == true)) {
				//ListにEmpleyee_idが1の情報を追加
				
				list.add(w);
				//DBの年月日と今日の年月日が一緒であればtodayWorkに出勤時間と退勤時間を追加
				dbToDay = sdf.format(w.getDay());
				toDay = sdf.format(new Date());
				if(dbToDay.equals(toDay) ){
					todayWork.add(w.getAttendancetime());
					todayWork.add(w.getLeavingtime());

				}
				if(w.getOvertime() != null) {
					String[] overTime;
					//残業時間をMMとmmに分割する
					overTime = w.getOvertime().split(":");
					//int型に変換して足していく
					sumHours += Integer.parseInt(overTime[0]);
					sumMinutes += Integer.parseInt(overTime[1]);
				}
			}
			yearMonth.add(sdf2.format(w.getDay()));
		}		
		//残業代計算
		sumHours += sumMinutes/60;
		sumMinutes = sumMinutes%60;
		String fmt = "%02d:%02d";
		thisOver = String.format(fmt, sumHours, sumMinutes);

		
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		//HTMLに送る
		
		showPayslip(payslipService, selectedYearMonth, model,paypayService);
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("thisMonth",selectedYearMonth);
		model.addAttribute("totalOverTime",thisOver);
		
		return id;
	}
	
	//給与設定用メソッド
	public Integer setPayslip(PayslipService payslipService,PayslipForm payslipForm) {
		Iterable<Payslip> pas = payslipService.selectI(id);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		java.sql.Date sqlDate=now();
		Payslip p2 = null;
		for(Payslip p :pas) {
			if (sdf2.format(p.getDay()).equals(thisMonth)) {
				p2 = p;
			}
		}
		if(thisMonth != sdf2.format(new Date())) {
			String[] m =thisMonth.split("/");
			sqlDate= java.sql.Date.valueOf(m[0] + "-" + m[1] + "-"+ "01");	
		}
		
		if(p2 == null) {
			payslipService.in(id, payslipForm.getBasepay(), sqlDate);
		}else {
			payslipService.up(payslipForm.getBasepay(),p2.getId());
		}
		
		return id;
	}
	
	//給与表示用メソッド
	public void showPayslip(PayslipService payslipService,String Kongetu,Model model,PaypayService paypayService) {
		Iterable<Payslip> pay = payslipService.selectI(id);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		Integer i = 0;
		//当月給与が確定済みかどうかを判別
		boolean b = true;
		for(Payslip p :pay) {
			if (sdf2.format(p.getDay()).equals(Kongetu)) {
				i = p.getBasepay();
			}
		}
		if(i == 0) {
			i = paypayService.selectBP(id);
			b = false;
		}
		/*残業代設定*/
		//時間と分の分割
		String[] over = thisOver.split(":");
		//時給の計算
		int hpay = i/160;
		//時間の残業代
		int h = Integer.parseInt(over[0]) * hpay;
		//分の残業代
		int m = Integer.parseInt(over[1]) * hpay / 60;
		
		model.addAttribute("over" ,(h + m));
		model.addAttribute("apo",b);
		model.addAttribute("plist",i);	
	}
	
	//基本給登録
	public Integer setpaypay(PaypayService paypayService,PaypayForm paypayForm) {
			java.sql.Date sqlDate=now();
		paypayService.save(id,paypayForm.getBasepay(),sqlDate);
		return id;
	}
	
	//給与確定
	public Integer onepay(PayslipService payslipService,PaypayService paypayService) {
			java.sql.Date sqlDate=now();
			
			payslipService.in(id,paypayService.selectBP(id),sqlDate);
		return id;
	}
	
	//現在時刻取得
	public java.sql.Date now(){
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
			return sqlDate;
	}
}

