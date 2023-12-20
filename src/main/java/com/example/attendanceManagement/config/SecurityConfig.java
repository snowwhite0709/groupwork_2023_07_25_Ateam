package com.example.attendanceManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.formLogin(form -> form
				.loginPage("/login") // ログイン画面の URL
				.loginProcessingUrl("/authenticate")// ユーザー名・パスワードの送信先 URL
				.defaultSuccessUrl("/defaultRedirect") // ログイン成功後のリダイレクト先 URL
				.failureUrl("/login?failure") // ログイン失敗後のリダイレクト先 URL
				.permitAll() // ログイン画面は未ログインでもアクセス可能
		).logout(logout -> logout
				.logoutSuccessUrl("/login?logout") // ログアウト成功後のリダイレクト先 URL
		).authorizeHttpRequests(authz -> authz
				.requestMatchers("/login").permitAll()// 「/login」はすべて許可
				// URL ごとに Role の権限を設定
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/management/**").hasRole("MANAGER")
				.requestMatchers("/employee/**").hasRole("EMPLOYEE")
				.anyRequest().authenticated() // 他の URL はログイン後のみアクセス可能
		).exceptionHandling((exceptionHandling) -> exceptionHandling
				.accessDeniedPage("/error"));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
//		System.out.println(new BCryptPasswordEncoder().encode("admin"));//user_id1
//		System.out.println(new BCryptPasswordEncoder().encode("manager"));//user_id2
//		System.out.println(new BCryptPasswordEncoder().encode("employee"));//user_id3
		return new BCryptPasswordEncoder();
	}

    @Bean
    public RoleHierarchy roleHierarchy() {
		// ロール階層の設定
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String hierarchy = "ROLE_ADMIN > ROLE_MANAGER \n ROLE_MANAGER > ROLE_EMPLOYEE";
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy;
	}
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("admin").password("{noop}password").roles("ADMIN","MANAGER","EMPLOYEE")
//                .and()
//                .withUser("manager").password("{noop}password").roles("MANAGER","EMPLOYEE")
//                .and()
//                .withUser("employee").password("{noop}password").roles("EMPLOYEE");
//    }

}
