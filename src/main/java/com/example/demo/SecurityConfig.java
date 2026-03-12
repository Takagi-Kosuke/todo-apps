package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login").permitAll() // ログイン画面は誰でもOK
						.anyRequest().authenticated() // その他は要ログイン
				)
				.formLogin(form -> form
						.loginPage("/login") // 自作ログイン画面
						.defaultSuccessUrl("/", true) // 成功したらトップへ
						.permitAll())
				.logout(logout -> logout.permitAll());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		// パスワードをしっかり暗号化してメモリに保存
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
		
		UserDetails admin = User.builder()
		        .username("admin") // 2人目
		        .password(passwordEncoder().encode("admin123"))
		        .roles("USER")
		        .build();

		return new InMemoryUserDetailsManager(user,admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// これが「パスワードを照合するものさし」になります
		return new BCryptPasswordEncoder();
	}
}