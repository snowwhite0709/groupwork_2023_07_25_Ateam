package com.example.attendanceManagement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.attendanceManagement.entity.Admin;
import com.example.attendanceManagement.entity.User_table;
import com.example.attendanceManagement.repository.AdminRepository;
import com.example.attendanceManagement.repository.User_tableRepository;
import com.example.attendanceManagement.service.userdetails.UserDetailsImpl;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	public static Integer USERID;
	
	
	
	@Autowired
	User_tableRepository user_tableRepository;
	@Autowired
	AdminRepository adminRepository;

	@Autowired  // パスワードをハッシュ化メソッド
	private PasswordEncoder passwordEncoder;
	
//	Integer id, String password, String lastname,String firstname,Integer sex,Integer age,Integer status,Integer rank,Integer admin
//	public void registerUser(String password, String lastname,String firstname,Integer sex,Integer age,Integer status,Integer rank,Integer admin) {
	    // ユーザー名の重複チェックなどの処理を追加することが望ましい
	
//	    // パスワードをハッシュ化
//	    String hashedPassword = passwordEncoder.encode(password);
//	
//	    // ユーザーエンティティを作成
//	    User_table newUser = new  User_table(id,hashedPassword, lastname,firstname, sex, age, status, rank,admin);
//	    
//	    System.out.println("a");
	    // ユーザーを保存
	   // loginUserRepository.save(newUser);
	   
	    
//	    User_table user = new  User_table(null, passwordEncoder.encode(password), lastname,firstname, sex, age, status, rank,admin);
//		
//	    user_tableRepository.insertLoginUser(user.getPass(), user.getLastname(),user.getFirstname(),user.getSex(),user.getAge(),user.getStatus(),user.getRank(),user.getAdmin());
//	
//	    
//	    
//	    System.out.println("b");
//	    
//	    
//	}
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username:" + username);
		USERID = Integer.parseInt(username);
		Integer val = Integer.valueOf(username);
		// login_user テーブルから username に対応するデータを取得する
		Optional<User_table> loginUserOpt = user_tableRepository.findById(val);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (loginUserOpt.isPresent()) {
			// ログインユーザーが存在するとき
			// 権限データを取得
			Iterable<Admin> adoIte = adminRepository.findAll();
			Integer adminId = loginUserOpt.get().getId(); //getidでいいのかの確認
			for (Admin ad : adoIte) {
				if (adminId == ad.getId()) {
					// ユーザーの権限に対応する権限名を設定する
					// "ROLE_◯◯"の名前で設定すると hasRole の権限になる
					// "ROLE_"をつけない場合は hasAuthority の権限になる
					System.out.println("ROLE_" + ad.getName());

					authorities.add(new SimpleGrantedAuthority("ROLE_" + ad.getName()));
				}
			}
		}
		return new UserDetailsImpl(loginUserOpt.get(), authorities);
	}
}
