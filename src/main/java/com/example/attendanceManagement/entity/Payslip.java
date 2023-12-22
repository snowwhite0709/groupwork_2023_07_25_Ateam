package com.example.attendanceManagement.entity;

import java.sql.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               // Getter,Setterが不要になる
@NoArgsConstructor  // デフォルトコンストラクターの自動生成
@AllArgsConstructor // 全フィールドに対する初期化値を引数に取るコンストラクタを自動生成
public class Payslip {
	@Id
	private Integer id;
	//private String  firstname;
	//private String lastname;
	private Integer basepay; //基本給
	private Integer overtimepay; //残業
	private Integer executive; //資格手当
	//private Integer position;//役職手当
	//private Integer qualification; //住宅手当
	//private Integer housing; //家族手当
	//private Integer familly;//通勤手当
	//private Integer advancetransportation; //立替交通費
	private Integer texableamount; //課税支給額合計
	private Integer taxfreepaymentamount; //非課税支給額合計
	private Integer total; //総支給
	private Date day;//日付
	 
	/*private Integer health; //健康保険
	private Integer welfarepension; //厚生年金
	private Integer employment;  //雇用保険
	private Integer incometax; //所得税
	private Integer residenttax; //住民税
	private Integer asset; //財形貯蓄
	private Integer life; //生命保険
	private Integer deposit; //積立金
	private Integer union_fee; //組合費
	private Integer others; //その他
	private Integer totalamount; //控除額合計
	*/

}
