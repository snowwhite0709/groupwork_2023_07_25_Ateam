package com.example.attendanceManagement.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.attendanceManagement.entity.Shift_table;
import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.form.Shift_tableForm;
import com.example.attendanceManagement.service.Shift_tableService;
import com.example.attendanceManagement.service.User_tableService;
import com.example.attendanceManagement.service.userdetails.UserDetailsImpl;

@Controller
public class Shift_tableController {
 
	@Autowired
	User_tableService user_tableService;
	@Autowired
	Shift_tableService shift_tableService;

	@ModelAttribute
	public Shift_tableForm setUpShiftForm() {
		Shift_tableForm form = new Shift_tableForm();
		return form;
	}

	//ユーザー側のシフト申請、来月の申請
	@GetMapping("/employee/shiftcreate")
	public String Shift_tableCtrate(Model model, Authentication auth) {

		/*現在のユーザー情報を取得*/
		// UserDetailsを取り出す
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		// ユーザーIDを取得
		Integer userId = (Integer) userDetails.getUser_Id();
		String username = (String) userDetails.getUsername();
		//System.out.println("UserDetailUserId"+userId);

		LocalDate currentDate = LocalDate.now();

		// 来月の年月を取得
		YearMonth nextMonth = YearMonth.from(currentDate).plusMonths(1);

		// 来月の初日を取得
		LocalDate firstDayOfNextMonth = nextMonth.atDay(1);

		// 来月の日数を取得
		int numberOfDaysInNextMonth = nextMonth.lengthOfMonth();

		// 来月のすべての日付を取得
		List<LocalDate> nextMonthDates = new ArrayList<>();
		for (int i = 0; i < numberOfDaysInNextMonth; i++) {

			LocalDate date = firstDayOfNextMonth.plusDays(i);
			nextMonthDates.add(date);
		}
		// Shift_tableに必要な情報を追加
		List<String> dateList = new ArrayList<>();

		// 取得した日付を表示
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (LocalDate date : nextMonthDates) {

			//リストに追加
			dateList.add(date.format(formatter));
		}

		model.addAttribute("id", userId);
		model.addAttribute("username", username);
		model.addAttribute("dateList", dateList);
		model.addAttribute("month", nextMonth);
		return "shift";

	}

	//シフト申請の情報をDBに登録するメソッド
	@PostMapping("/employee/shiftinsert")
	public String insert(@Validated Shift_tableForm shift_tableForm,
			Model model, Authentication auth) {
		/*現在のユーザー情報を取得*/
		// UserDetailsを取り出す
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		// ユーザーIDを取得
		Integer employee_id = (Integer) userDetails.getUser_Id();
		String username = (String) userDetails.getUsername();

		LocalDate currentDate = LocalDate.now();

		// 来月の年月を取得
		YearMonth nextMonth = YearMonth.from(currentDate).plusMonths(1);

		// 来月の初日を取得
		LocalDate firstDayOfNextMonth = nextMonth.atDay(1);

		// 来月の日数を取得
		int numberOfDaysInNextMonth = nextMonth.lengthOfMonth();

		// 来月のすべての日付を取得
		List<LocalDate> nextMonthDates = new ArrayList<>();
		for (int i = 0; i < numberOfDaysInNextMonth; i++) {

			LocalDate date = firstDayOfNextMonth.plusDays(i);
			nextMonthDates.add(date);
		}
		Shift_table shift_table = null;
		// 取得した日付を表示
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM");
		String month = nextMonth.format(formatter2);

		for (LocalDate date : nextMonthDates) {

			shift_table = new Shift_table();
			shift_table.setEmployee_id(employee_id);
			shift_table.setUsername(username);
			shift_table.setYearmonth(date.format(formatter));
			shift_table.setWorkday(shift_tableForm.getWorkday());
			shift_table.setVariableattendance(shift_tableForm.getVariableattendance());
			shift_table.setKind(shift_tableForm.getKind());
			shift_table.setApproval(shift_tableForm.getApproval());
			shift_table.setMonth(month);
			shift_tableService.Insert(shift_table);

		}

		//		System.out.println("/shiftinsertOK");

		Iterable<Shift_table> shift_tableList = shift_tableService.SelectAll();

		model.addAttribute("shift_tableList", shift_tableList);
		//		System.out.println("shift_tableList" + shift_tableList);
		model.addAttribute("id", employee_id);
		//		System.out.println(" id" + employee_id);
		model.addAttribute("username", username);
		//		System.out.println("username" + username);
		model.addAttribute("month", month);

		return "shiftMonth";

	}

	//employee_idとyaermonthキーにしてデータを更新したい!!
	@PostMapping("/employee/shiftupdate")
	public String update(@Validated Shift_tableForm shift_tableForm, BindingResult result, Model model,
			RedirectAttributes redirectAtrributes) {
		Shift_table shift_table = new Shift_table();

		shift_table.setEmployee_id(shift_tableForm.getEmployee_id());
		shift_table.setYearmonth(shift_tableForm.getYearmonth());
		shift_table.setUsername(shift_tableForm.getUsername());
		shift_table.setWorkday(shift_tableForm.getWorkday());
		shift_table.setVariableattendance(shift_tableForm.getVariableattendance());
		shift_table.setKind(shift_tableForm.getKind());
		shift_table.setApproval(shift_tableForm.getApproval());
		shift_table.setMonth(shift_tableForm.getMonth());
		System.out.println("update");
		//入力チェック
		if (!result.hasErrors()) {
			//更新処理、フラッシュスコープの使用、リダイレクト
			System.out.println("updateOK");
			shift_tableService.Update(shift_table);
			redirectAtrributes.addFlashAttribute("complete", "更新が完了しました");
			//更新画面を表示
			return "redirect:shift";
		} else {
			makeshiftUpdateModel(shift_tableForm, model);
			return "shift";
		}
	}

	//更新用のModel作成
	private void makeshiftUpdateModel(Shift_tableForm shift_tableForm, Model model) {

		model.addAttribute("id", shift_tableForm.getEmployee_id());
		model.addAttribute("username", shift_tableForm.getYearmonth());
		model.addAttribute("yearmonth", shift_tableForm.getUsername());
		model.addAttribute("workday", shift_tableForm.getWorkday());
		model.addAttribute("variableattendance", shift_tableForm.getVariableattendance());
		model.addAttribute("kind", shift_tableForm.getKind());
		model.addAttribute("approval", shift_tableForm.getApproval());
		model.addAttribute("month", shift_tableForm.getMonth());
		shift_tableForm.setNewShift_table(true);
		model.addAttribute("shift_tableForm", shift_tableForm);
		model.addAttribute("title", "更新用フォーム");

	}

	private Shift_tableForm makeShift_tableForm(Shift_table shift_table) {
		Shift_tableForm shift_tableForm = new Shift_tableForm();

		shift_tableForm.setEmployee_id(shift_table.getEmployee_id());
		shift_tableForm.setYearmonth(shift_table.getYearmonth());
		shift_tableForm.setUsername(shift_table.getUsername());
		shift_tableForm.setWorkday(shift_table.getWorkday());
		shift_tableForm.setVariableattendance(shift_table.getVariableattendance());
		shift_tableForm.setKind(shift_table.getKind());
		shift_tableForm.setApproval(shift_table.getApproval());
		shift_tableForm.setMonth(shift_table.getMonth());
		shift_tableForm.setNewShift_table(false);

		return shift_tableForm;
	}

	//管理者側シフト表　IDとMonthで検索し未承認のシフトを取得
	@GetMapping("/management/selectShiftMonth")
	public String selectShiftMonth(Model model) {
		//ユーザーIDや名前を取得
		Iterable<User_table> userlist = user_tableService.SelectAll();
		//シフトテーブル内のMonthを全取得
		Iterable<Shift_table> shiftlist = shift_tableService.SelectAll();
		Set<String> monthlist = new TreeSet<>();

		for (Shift_table s : shiftlist) {
			monthlist.add(s.getMonth());
		}

		//HTMLへ送る

		model.addAttribute("monthlist", monthlist);

		model.addAttribute("userlist", userlist);

		return "selectShiftMonth";
	}

	//選ばれたユーザーIDを使ってシフトテーブルを取得
	@PostMapping("/management/submitShiftSelect")
	public String selectUserMonth(@RequestParam("selectedId") Integer employee_id,
			@RequestParam("selectedMonth") String month, Model model) {

		Iterable<Shift_table> selectlist = shift_tableService.idMonthAll(employee_id, month);
		String username = shift_tableService.idMonthUsernameSelect(employee_id, month);
//		System.out.println(selectlist);
//		System.out.println("username" + username);
//		System.out.println("employee_id" + employee_id);
//		System.out.println("month" + month);
		model.addAttribute("id", employee_id);
		model.addAttribute("username", username);
		model.addAttribute("month", month);
		model.addAttribute("selectlist", selectlist);

		return "selectShiftMonth";
	}

}
