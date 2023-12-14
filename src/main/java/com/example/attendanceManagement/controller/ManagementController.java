package com.example.attendanceManagement.controller;

import java.util.Optional;

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
import com.example.quiz.entity.Quiz;
import com.example.quiz.form.QuizForm;

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
		Iterable<User_table> list = user_tableService.SelectAll();

		model.addAttribute("list",list);

		return "accountmanagement";
	}

	//アカウント新規作成画面へ遷移するためのメソッド
	@GetMapping("/accountedit")
	public String accauntManagement() {
		return "accountmake";
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
			user_tableService.Insert(user_table);
			redirectAttributes.addFlashAttribute("complete","登録が完了しました");
			return "redirect:/management/accountedit";
		}else {
			return null;
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

//Quizデータを1件取得し、フォーム内に表示する
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
		return "crud";
	}
	
	//更新用のModel作成
	private void makeUpdateModel(User_tableForm user_tableForm, Model model) {
		model.addAttribute("id", user_tableForm.getId());
		user_tableForm.setNewUser_table(false);
		model.addAttribute("quizForm", user_tableForm);
		model.addAttribute("title","更新用フォーム");
	}
	
	//idをキーにしてデータを更新
	@PostMapping("/update")
	public String update(@Validated QuizForm quizForm, BindingResult result, Model model,
			RedirectAttributes redirectAtrributes) {
		Quiz quiz = makeQuiz(quizForm);
		//入力チェック
		if(!result.hasErrors()) {
			//更新処理、フラッシュスコープの使用、リダイレクト
			service.updateQuiz(quiz);
			redirectAtrributes.addFlashAttribute("complete","更新が完了しました");
			//更新画面を表示
			return "redirect:/quiz/" + quiz.getId();
		}else {
			makeUpdateModel(quizForm, model);
			return "crud";
		}
	}
	
	
	private Quiz makeQuiz(QuizForm quizForm) {
		Quiz quiz = new Quiz();
		quiz.setId(quizForm.getId());
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		return quiz;
	}
	
	private QuizForm makeUser_tableForm(Quiz quiz) {
		QuizForm form = new QuizForm();
		form.setId(quiz.getId());
		form.setQuestion(quiz.getQuestion());
		form.setAnswer(quiz.getAnswer());
		form.setAuthor(quiz.getAuthor());
		form.setNewQuiz(false);
		return form;
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model,
			RedirectAttributes redirectAttributes) {
		service.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delcomplete", "削除が完了しました");
		return "redirect:/quiz";
	}
}
