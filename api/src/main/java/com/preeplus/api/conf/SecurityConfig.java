package com.preeplus.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("test").password(passwordEncoder().encode("1234")).authorities("ROLE_ADMIN", "ROLE_USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.NEVER)
				.and()
				.authorizeRequests()
				.anyRequest().permitAll()
		.and();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

/*
	헤더에 x-auth-token 을 활성화 합니다.
	이건 추후 검증이 좀 더 필요해 보입니다.
	@Bean
	public HeaderHttpSessionIdResolver httpSessionStrategy() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
*/

	@Bean
	public HeaderHttpSessionStrategy headerHttpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}
}
