package com.example.attendanceManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.attendanceManagement.entity.Payslip;
import com.example.attendanceManagement.service.PayslipService;

@Controller
/*@RequestMapping("/payslip")*/
public class PayslipController {

	@Autowired
	private PayslipService payslipService;

	@GetMapping("/management/pay")
	public String calculateSalary(Model model) {
		System.out.println("paypaypya");
		Payslip payslip = payslipService.SlectOneById(1).orElse(null);
		model.addAttribute("pay", payslip);

		/*
		if (payslip != null) {
			// 各項目ごとに変数に代入
			int basepay = payslip.getBasepay();//基本給
		
			int overtimepay = payslip.getOvertimepay();//残業手当
		
			int position = payslip.getPosition();//役職手当
		
			int executive = payslip.getExecutive();//資格手当
		
			//int qualification = payslip.getQualification();//役職手当
		
			//int housing=payslip.getHousing();//住宅手当
		
			//int familly=payslip.getFamilly();//家族手当
		
			int advancetransportation=payslip.getAdvancetransportation();//通勤手当
		
			//int texableamount = payslip.getTexableamount();//課税支給額合計
		
			//int taxfreepaymentamount=payslip.getTaxfreepaymentamount();//非課税支給額合計
		
			//int total=payslip.getTotal();//総支給
		
			//int health=payslip.getHealth();//健康保険
		
			//int welfarepension=payslip.getWelfarepension(); //厚生年金
		
			//int employment=payslip.getEmployment(); //雇用保険
		
			//int incometax=payslip.getIncometax();//所得税
		
			//int residenttax = payslip.getResidenttax();//住民税
		
			//int asset=payslip.getAsset();//財形貯蓄
		
			//int life=payslip.getLife();//生命保険
		
			//int deposit=payslip.getDeposit();//積立金
		
			//int unionFee = payslip.getUnion_fee();//組合費
		
			//int others=payslip.getOthers();//その他
		
			//int totalamount=payslip.getTotalamount();//控除額合計
		
			// 各項目の合計を計算
			//課税支給額
			int calculateTotaltaxSalary=calculateTotaltaxSalary(basepay,overtimepay,position,executive);
			//非課税支給額
			int calculatetaxSalary=calculatetaxSalary(advancetransportation);
			//総支給
			int total = calculateTotalSalary(calculateTotaltaxSalary,calculatetaxSalary);
		
			//int totalDeductions = calculateTotalDeductions(health, welfarepension, employment,incometax,asset,life,deposit, texableamount, residenttax, unionFee);
		
			model.addAttribute("basepay", basepay);
			model.addAttribute("Overtime Pay: " , overtimepay);
			model.addAttribute("Position: " , position);
			model.addAttribute("Executive:",executive);
			model.addAttribute("Total: ", total);
			model.addAttribute("calculateTotaltaxSalary",calculateTotaltaxSalary);
			model.addAttribute("calculatetaxSalary",calculatetaxSalary);
		
			// 各項目を文字列として返す
			return "payslip";
					//"Position: " + position + "<br>" +
					//"Executive:"+executive+"<br>"+
					//"Qualification:"+qualification+"<br>"+
					//"Housing:"+housing+"<br>"+
					//"Familly:"+familly+"<br>"+
					//"AdvanceTransportation:"+advancetransportation+"<br>"+
					//"Total: " + total + "<br><br>" ;
		
					//"Health:"+health+"<br>"+
					//"Welfarepension:"+welfarepension+"<br>"+
					//"Employment:"+employment+"<br>"+
					//"Incometax:"+incometax+"<br>"+
					//"Asset:"+asset+"<br>"+
					//"Life:"+life+"<br>"+
					//"Deposit"+deposit+"<br>"+
					//"Texable Amount: " + texableamount + "<br>" +
					//"Resident Tax: " + residenttax + "<br>" +
					//"Union Fee: " + unionFee + "<br>" +
					//"Total Deductions: " + totalDeductions + "<br><br>" +
					//"Net Salary: " + (totalSalary - totalDeductions);
		} else {
			return "Employee not found";
		}*/
		return "payslip";
	}

	//総支給
	private int calculateTotalSalary(int calculateTotaltaxSalary,int calculatetaxSalary) {
		// 総支給の合計を計算するロジックを実装
		return calculateTotaltaxSalary+calculatetaxSalary;
	}

	//課税支給額
	private int calculateTotaltaxSalary(int basepay,int overtimepay,int position,int executive ) {
		// ここで基本給や手当の合計を計算するロジックを実装
		return basepay + overtimepay + position + executive; 
	}
	//非課税支給額
	private int calculatetaxSalary(int advancetransportation) {
		return advancetransportation;
	}


	/*private int calculatetaxfreepaymentamount=(int taxfreepaymentamount){
			return taxfreepaymentamount;
		};*/
	/*private int calculateTotalDeductions(int health,int welfarepension,int employment,int incometax,int asset,int life,
			int deposit,int texableamount, int residenttax, int unionFee) {
	    // ここで控除の合計を計算するロジックを実装
	    return health+welfarepension+employment+incometax+texableamount+asset+life+deposit + residenttax + unionFee;
	}*/
}


