package com.example.attendanceManagement.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.entity.Work;
import com.example.attendanceManagement.form.User_tableForm;
import com.example.attendanceManagement.service.User_tableService;
import com.example.attendanceManagement.service.WorkService;

@Controller
@RequestMapping("/management")
public class ManagementController {

	@Autowired
	User_tableService user_tableService;
	@Autowired
	WorkService workService;
	
	@ModelAttribute
	public User_tableForm setUpForm() {
		User_tableForm form = new User_tableForm();
		return form;
	}

	//トップページ（勤怠承認画面）へ遷移するためのメソッド
	@GetMapping	
	public String managementPage(Model model) {
		Iterable<Work> list = workService.getWork();
		model.addAttribute("list",list);
		return "managementpage";
	}

	//アカウント管理画面へ遷移するためのメソッド
	@GetMapping("/accountmanagement")
	public String accountmanagement(User_tableForm user_tableForm, Model model) {
		Iterable<User_table> list = user_tableService.getAll();
		model.addAttribute("list",list);
		return "accountmanagement";
	}

	//アカウント新規作成画面へ遷移するためのメソッド
	@GetMapping("/accountedit")
	public String accauntManagement() {
		return "accountmake";
	}
	

	//給与管理画面へ遷移するためのメソッド
	@GetMapping("/pay")
	public String pay() {
		return "payslip";
	}
	
	//勤怠登録画面へ遷移するためのメソッド
	@GetMapping("/attendanceregistration/{id}")
	public String atten(@PathVariable Integer id, Model model) {
    	
		List<String> todayWork = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String dbToDay;
		String toDay;
		
		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		//今月の年月を取得
		String Kongetu = sdf2.format(new Date());
		
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
		model.addAttribute("list", list);
		model.addAttribute("todayWork",todayWork);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		
        return "attendanceregistration";
    }
	@PostMapping("/submitForm")
    public String handleFormSubmission(@PathVariable Integer id,@RequestParam("yearMonth") String selectedYearMonth,
    		Model model,RedirectAttributes redirectAttributes) {
		
		Set<String>yearMonth = new TreeSet<>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM");
		System.out.println("ここまで");
		
		//workテーブルの情報を取得
		Iterable<Work> work = workService.SelectAll();
		//List型を宣言
		List<Work> list = new ArrayList<>();
		//Listに要素を詰め込む
		for(Work w : work) {
			//指定したemployee_idの当月の勤怠情報を取得
			if (w.getEmployee_id() == id && sdf2.format(w.getDay()).equals(selectedYearMonth)) {
				//ListにEmpleyee_idが1の情報を追加
				list.add(w);
				yearMonth.add(sdf2.format(w.getDay()));
			}
		}	
		Collections.sort(list, (d1, d2) -> d1.getDay().compareTo(d2.getDay()));
		
		//HTMLに送る
		model.addAttribute("list", list);
		model.addAttribute("workingDays", list.size());
		model.addAttribute("yearMonth", yearMonth);
		model.addAttribute("selectedYearMonth", selectedYearMonth);

        // 他の処理や遷移先を返す
        return "redirect:attendanceregistration";
    }

	//アカウント新規作成の情報登録用メソッド
	@PostMapping("/insert")
	public String insert(@Validated User_tableForm user_tableForm, BindingResult bindingResult,
			Model model, RedirectAttributes redirectAttributes) {
		User_table user_table = new User_table();
		user_table.setPass(user_tableForm.getPass());
		user_table.setLastname(user_tableForm.getLastname());
		user_table.setFirstname(user_tableForm.getFirstname());
		user_table.setSex(user_tableForm.getSex());
		user_table.setAge(user_tableForm.getAge());
		user_table.setStatus(user_tableForm.getStatus());
		user_table.setRank(user_tableForm.getRank());
		user_table.setAdmin(user_tableForm.getAdmin());

		
		if(!bindingResult.hasErrors()) {
			user_tableService.sAll(user_table);
			redirectAttributes.addFlashAttribute("complete","登録が完了しました");
			return "redirect:/management/accountedit";
		}else {
			return "accountmake";
		}
	}


//トップページで、未承認の勤怠情報を承認するためのメソッド
@PostMapping("/{id}")
public String approval(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
	Work work = new Work();
	Optional<Work> w = workService.SlectOneById(id);
	work = w.get();
	work.setApproval(true);
	workService.UpdateWork(work);
	redirectAttributes.addFlashAttribute("complete","登録が完了しました");
	return "redirect:/management";
}

//データを1件取得し、フォーム内に表示する
	@GetMapping("/accountedit/{id}")
	public String showUpDate(User_tableForm user_tableForm, @PathVariable Integer id, Model model) {
		//Quiz取得
		Optional<User_table> user_tableOpt = user_tableService.SlectOneById(id);
		
		//QuizFormへの詰めなおし
		Optional<User_tableForm> user_tableFormOpt = user_tableOpt.map(t -> makeUser_tableForm(t));
		//QuizがNullでなければ中身を取り出す
		if(user_tableFormOpt.isPresent()) {
			user_tableForm = user_tableFormOpt.get();
		}
		//更新用のModel作成
		makeUpdateModel(user_tableForm, model);
		return "accountmake";
	}
	
	//更新用のModel作成
	private void makeUpdateModel(User_tableForm user_tableForm, Model model) {
		
		model.addAttribute("lastname", user_tableForm.getLastname());
		model.addAttribute("firstname", user_tableForm.getFirstname());
		model.addAttribute("sex", user_tableForm.getSex());
		model.addAttribute("age", user_tableForm.getAge());
		model.addAttribute("status", user_tableForm.getStatus());
		model.addAttribute("rank", user_tableForm.getRank());
		model.addAttribute("admin", user_tableForm.getAdmin());
		user_tableForm.setNewUser_table(false);
		model.addAttribute("user_tableForm", user_tableForm);
		model.addAttribute("title","更新用フォーム");
	}

	//idをキーにしてデータを更新
	@PostMapping("/update")
	public String update(@Validated User_tableForm user_tableForm, BindingResult result, Model model,
			RedirectAttributes redirectAtrributes) {
		User_table user_table = new User_table();
		user_table.setId(user_tableForm.getId());
		user_table.setPass(user_tableForm.getPass());
		user_table.setLastname(user_tableForm.getLastname());
		user_table.setFirstname(user_tableForm.getFirstname());
		user_table.setSex(user_tableForm.getSex());
		user_table.setAge(user_tableForm.getAge());
		user_table.setStatus(user_tableForm.getStatus());
		user_table.setRank(user_tableForm.getRank());
		user_table.setAdmin(user_tableForm.getAdmin());
		//入力チェック
		if(!result.hasErrors()) {
			//更新処理、フラッシュスコープの使用、リダイレクト
			user_tableService.Update(user_table);
			redirectAtrributes.addFlashAttribute("complete","更新が完了しました");
			//更新画面を表示
			return "redirect:accountmanagement";
		}else {
			makeUpdateModel(user_tableForm, model);
			return "accountmake";
		}
	}
	
	private User_tableForm makeUser_tableForm(User_table user_table) {
		User_tableForm user_tableForm = new User_tableForm();
		user_tableForm.setId(user_table.getId());
		user_tableForm.setPass(user_table.getPass());
		user_tableForm.setLastname(user_table.getLastname());
		user_tableForm.setFirstname(user_table.getFirstname());
		user_tableForm.setSex(user_table.getSex());
		user_tableForm.setAge(user_table.getAge());
		user_tableForm.setStatus(user_table.getStatus());
		user_tableForm.setRank(user_table.getRank());
		user_tableForm.setAdmin(user_table.getAdmin());
		user_tableForm.setNewUser_table(false);
		return user_tableForm;
	}
	
	/*@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model,
			RedirectAttributes redirectAttributes) {
		user_tableService.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました");
		return "redirect:/quiz";
	}*/
}
