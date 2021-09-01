package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration //xác định lớp WebSecurityConfig của ta là một lớp dùng để cấu hình
@EnableWebSecurity //sẽ kích hoạt việc tích hợp Spring Security với Spring MVC.
public class MyConfig extends WebSecurityConfigurerAdapter{
	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailsServiceImpl();
	}
	// mã hóa password
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(this.getUserDetailService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin/**")// Khai báo đường dẫn req
		.hasRole("ADMIN") // chỉ cho phép các Author có ROLE_ADMIN truy cập
		.antMatchers("/user/**")
		.hasRole("USER")
		.antMatchers("/**")
		.permitAll() // cho phép tất cả các user đều dc truy cập
		.and()
		.formLogin()
		.loginPage("/signin") // dẫn tới trang đăng nhập
		.loginProcessingUrl("/dologin")
		.defaultSuccessUrl("/user/index") // đường dẫn tới trang đăng nhập thành công
		//.failureUrl("/login-fail") //đường dẫn tới trang đăng nhập thất bại
		.and()
		.csrf()
		.disable();
	}
	
}
