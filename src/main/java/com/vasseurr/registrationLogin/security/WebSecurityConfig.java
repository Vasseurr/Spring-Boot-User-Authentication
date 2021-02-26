package com.vasseurr.registrationLogin.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.activation.DataSource;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.vasseurr.registrationLogin.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	//when we use bean, 
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}
	
	//password encoder
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//authentication provider
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	//configure login and logout 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//http is an http security object
		http.authorizeRequests()
			.antMatchers("/list_users").authenticated()	//user must login to view the content
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.defaultSuccessUrl("/home")
				.permitAll()
			.and()
			.logout().logoutSuccessUrl("/").permitAll()
			.and()
				.csrf()
			.and()
			.rememberMe()
			//.key("uniqueAndSecret")
			//.rememberMeParameter("remember")	//it is name of checkbox at login page
			//.rememberMeCookieName("rememberlogin") //it is name of the cookie
			//.tokenValiditySeconds(100)				//remember for number of seconds 
			.tokenRepository(persistentTokenRepository())
			.tokenValiditySeconds(1209600);
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return null;
	}
	
}
