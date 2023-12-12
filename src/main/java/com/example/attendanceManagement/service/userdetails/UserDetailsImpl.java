package com.example.attendanceManagement.service.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.attendanceManagement.entity.LoginUser;

public class UserDetailsImpl implements UserDetails {
	// ユーザー情報クラス
	private final LoginUser loginUser;
	// 権限コレクション
	private Collection<GrantedAuthority> authorities;
	// コンストラクタ
	public UserDetailsImpl(LoginUser loginUser, Collection<GrantedAuthority> authorities) {
	this.loginUser = loginUser;
	this.authorities = authorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	// 権限のコレクションを返す
	return authorities;
	}
	@Override
	public String getPassword() {
	// ハッシュ化済みのパスワードを返す
	return loginUser.getPassword();
	}
	@Override
	public String getUsername() {
	// ログインで利用するユーザー名を返す
	return loginUser.getUsername();
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
	}
