package com.example.attendanceManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(form -> form
				.loginPage("/login") // ログイン画面の URL
				.loginProcessingUrl("/authenticate")// ユーザー名・パスワードの送信先 URL
				.defaultSuccessUrl("/loginsuccess") // ログイン成功後のリダイレクト先 URL
				.failureUrl("/login?failure") // ログイン失敗後のリダイレクト先 URL
				.permitAll() // ログイン画面は未ログインでもアクセス可能
		).logout(logout -> logout
				.logoutSuccessUrl("/login?logout") // ログアウト成功後のリダイレクト先 URL
		).authorizeHttpRequests(authz -> authz
				.requestMatchers("/login").permitAll()// 「/login」はすべて許可
				// URL ごとに Role の権限を設定
				
				.anyRequest().authenticated() // 他の URL はログイン後のみアクセス可能
				).exceptionHandling((exceptionHandling) -> exceptionHandling
						.accessDeniedPage("/error")
				);
		return http.build();
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
////	 System.out.println(new BCryptPasswordEncoder().encode("syain"));
//	return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	public RoleHierarchy roleHierarchy() {
//	// ロール階層の設定
//	 RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//	 String hierarchy = "ROLE_BUCHO > ROLE_KACHO \n ROLE_KACHO > ROLE_SYAIN";
//	 roleHierarchy.setHierarchy(hierarchy);
//	 return roleHierarchy;
//	}
}
