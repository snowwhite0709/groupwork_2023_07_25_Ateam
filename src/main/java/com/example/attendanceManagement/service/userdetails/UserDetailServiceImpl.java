package com.example.attendanceManagement.service.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.attendanceManagement.entity.LoginUser;
import com.example.attendanceManagement.entity.Role;
import com.example.attendanceManagement.repository.LoginUserRepository;
import com.example.attendanceManagement.repository.RoleRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
 @Autowired
 LoginUserRepository loginUserRepository;
 @Autowired
 RoleRepository roleRepository;
 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 // login_user テーブルから username に対応するデータを取得する
 Optional<LoginUser> loginUserOpt = loginUserRepository.findById(username);
 Collection<GrantedAuthority> authorities = new ArrayList<>();
 if (loginUserOpt.isPresent()) {
 // ログインユーザーが存在するとき
 // 権限データを取得
 Iterable<Role> roleIte = roleRepository.findAll();
 Integer roleId = loginUserOpt.get().getRoleId();
 for (Role role : roleIte) {
 if (roleId == role.getId()) {
 // ユーザーの権限に対応する権限名を設定する
 // "ROLE_◯◯"の名前で設定すると hasRole の権限になる
 // "ROLE_"をつけない場合は hasAuthority の権限になる
 authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
 }
 }
 }
 return new UserDetailsImpl(loginUserOpt.get(), authorities);
 }
}
