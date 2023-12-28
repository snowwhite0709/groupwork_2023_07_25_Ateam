package com.example.attendanceManagement.service.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.attendanceManagement.entity.User_table;
 


public class UserDetailsImpl implements UserDetails {
	// ユーザー情報クラス
	private final User_table user_table;
	// 権限コレクション
	private Collection<GrantedAuthority> authorities;
	// コンストラクタ
	public UserDetailsImpl(User_table user_table, Collection<GrantedAuthority> authorities) {
	this.user_table = user_table;
	this.authorities = authorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	// 権限のコレクションを返す
	return authorities;
	}
	@Override
	public String getPassword() {
	// ハッシュ化済みのパスワードを返す?
	return user_table.getPass();
	}
	@Override
	public String getUsername() {
	// ログインで利用するユーザー名を返す
		String name=user_table.getFirstname()+user_table.getLastname();
		//System.out.println(name);
	return name;
	}
	@Override
	public boolean isAccountNonExpired() {
	// ユーザーが期限切れでなければ true を返す
	return true;
	}
	@Override
	public boolean isAccountNonLocked() {
	// ユーザーがロックされていなければ true を返す
	return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
	// ユーザーのパスワードの期限が切れていなければ true を返す
	return true;
	}
	@Override
	public boolean isEnabled() {
	// ユーザーが有効であれば true を返す
	return true;
	}
	public Integer getUser_Id() {
		// TODO 自動生成されたメソッド・スタブ
		return user_table.getId();
	}

	}
