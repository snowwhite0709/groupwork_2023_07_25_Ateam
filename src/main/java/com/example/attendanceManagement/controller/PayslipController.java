package com.example.attendanceManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.attendanceManagement.entity.Payslip;
import com.example.attendanceManagement.service.PayslipService;

@RestController
@RequestMapping("/payslip")
public class PayslipController {

    @Autowired
    private PayslipService payslipService;

    @GetMapping("/calculate/{id}")
    public String calculateSalary(@PathVariable Integer id) {
        Payslip payslip = payslipService.SlectOneById(id).orElse(null);

        if (payslip != null) {
            // 各項目ごとに変数に代入
            int basepay = payslip.getBasepay();//基本給
            
            int overtimepay = payslip.getOvertimepay();//役職手当
            
            int position = payslip.getPosition();//資格手当
            
            int executive = payslip.getExecutive();//役職手当
            
            int qualification = payslip.getQualification();//住宅手当
            
            int housing=payslip.getHousing();//家族手当
            
            int familly=payslip.getFamilly();//通勤手当
            
            int advancetransportation=payslip.getAdvancetransportation();//立替交通費
            
            int texableamount = payslip.getTexableamount();//課税支給額合計
            
            int taxfreepaymentamount=payslip.getTaxfreepaymentamount();//非課税支給額合計
            
            int total=payslip.getTotal();//総支給
            
            int health=payslip.getHealth();//健康保険
            
            int welfarepension=payslip.getWelfarepension(); //厚生年金
            
            int employment=payslip.getEmployment(); //雇用保険
            
            int incometax=payslip.getIncometax();//所得税
            
            int residenttax = payslip.getResidenttax();//住民税
            
            int asset=payslip.getAsset();//財形貯蓄
            
            int life=payslip.getLife();//生命保険
            
            int deposit=payslip.getDeposit();//積立金
            
            int unionFee = payslip.getUnion_fee();//組合費
            
            int others=payslip.getOthers();//その他
            
            int totalamount=payslip.getTotalamount();//控除額合計

            // 各項目の合計を計算
            
            int totalSalary = calculateTotalSalary(basepay, overtimepay, executive,qualification,housing,familly,
            		advancetransportation,texableamount,total);
            
            int totalDeductions = calculateTotalDeductions(health, welfarepension, employment,incometax,asset,life,deposit, texableamount, residenttax, unionFee);

            // 各項目を文字列として返す
            return "Base Pay: " + basepay + "<br>" +
                   "Overtime Pay: " + overtimepay + "<br>" +
                   "Executive:"+executive+"<br>"+
                   "Qualification:"+qualification+"<br>"+
                   "Housing:"+housing+"<br>"+
                   "Familly:"+familly+"<br>"+
                   "AdvanceTransportation:"+advancetransportation+"<br>"+
                   "Total Salary: " + totalSalary + "<br><br>" +
                   
                   "Health:"+health+"<br>"+
                   "Welfarepension:"+welfarepension+"<br>"+
                   "Employment:"+employment+"<br>"+
                   "Incometax:"+incometax+"<br>"+
                   "Asset:"+asset+"<br>"+
                   "Life:"+life+"<br>"+
                   "Deposit"+deposit+"<br>"+
                   "Texable Amount: " + texableamount + "<br>" +
                   "Resident Tax: " + residenttax + "<br>" +
                   "Union Fee: " + unionFee + "<br>" +
                   "Total Deductions: " + totalDeductions + "<br><br>" +
                   "Net Salary: " + (totalSalary - totalDeductions);
        } else {
            return "Employee not found";
        }
    }

    private int calculateTotalSalary(int basepay, int overtimepay, int executive, int qualification, int housing, 
    		int familly, int advancetransportation, int texableamount, int total) {
        // ここで基本給や手当の合計を計算するロジックを実装
        return basepay + overtimepay + executive+qualification+qualification+housing+familly+
        		advancetransportation+texableamount+total;
    }

	/*private int calculatetaxfreepaymentamount=(int taxfreepaymentamount){
			return taxfreepaymentamount;
		};*/
    private int calculateTotalDeductions(int health,int welfarepension,int employment,int incometax,int asset,int life,
    		int deposit,int texableamount, int residenttax, int unionFee) {
        // ここで控除の合計を計算するロジックを実装
        return health+welfarepension+employment+incometax+texableamount+asset+life+deposit + residenttax + unionFee;
    }
}
