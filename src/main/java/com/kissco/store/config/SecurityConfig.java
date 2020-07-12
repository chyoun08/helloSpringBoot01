package com.kissco.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity	//보안 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override

	protected void configure(HttpSecurity http) throws Exception {
		
		http
	  		.authorizeRequests()		//권한 필요로하는 request들을 받겠다.
	  		.anyRequest()				//어떤 request든 모두
	  		.permitAll()				//해당 권한을 전부 허용한다
	  		.and().csrf().disable();		//그리고 추가적으로 csrf토큰을 숨겨서 보내겠다.

	}
}
