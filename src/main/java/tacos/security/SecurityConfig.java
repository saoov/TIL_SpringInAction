package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	/*
	 * 모든 사용자의 홈페이지 접근을 허용
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/design","/orders")
		.access("hasRole('ROLE_USER')")
		.antMatchers("/","/**").access("permitAll")
		
		.and()
		.formLogin() //커스텀 로그인 폼을 구성하기 위해 호출
		.loginPage("/login") //커스텀 로그인 페이지 경로를 지정
		
		.and()
		.logout() //로그아웃
		.logoutSuccessUrl("/") //로그아웃 후 이동경로
		
		.and()
		.csrf(); //csrf지원
	}

	/*
	 * user1, user2를 '인메모리' 사용자 스토어에 구성하였다.
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(encoder());
		
	/*	auth.jdbcAuthentication() //jdbc 사용자 스토어
		.dataSource(dataSource)
		.usersByUsernameQuery("select username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("select username, authority from authorities where username=?")
		.passwordEncoder(new NoEncodingPasswordEncoder());
	*/
		
		
	/*	auth.inMemoryAuthentication() //인메모리 사용자 스토어
		.withUser("user1") //사용자의 구성 시작 메소드
		.password("{noop}password1")
		.authorities("ROLE_USER") //부여 권한
		.and()
		.withUser("user2")
		.password("{noop}password2")
		.authorities("ROLE_USER");
	*/
	}
	
	

}
