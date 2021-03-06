
package com.hung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.hung.config.jwt.JwtAuthenticationFilter;
import com.hung.service.impls.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] AUTH_LIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**"
	};
	
	@Autowired
	UserServiceImpl userServiceImpl;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)

	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/api/books").permitAll() 
			.antMatchers(HttpMethod.GET,"/api/comments").permitAll() 
			.antMatchers(HttpMethod.GET,"/api/books/{id}").permitAll()
			.antMatchers(HttpMethod.GET,"/api/comments/{id}").permitAll()
			.antMatchers("/api/login").permitAll()
			.antMatchers(AUTH_LIST).permitAll()
				/*
				 * .antMatchers("/webjars/**").permitAll()
				 * .antMatchers("/swagger-resources/**").permitAll()
				 * .antMatchers("/v2/api-docs").permitAll()
				 * .antMatchers("/swagger-ui.html").permitAll()
				 */
			.antMatchers("/api/users", "/api/roles").hasRole("ADMIN")
			.anyRequest().authenticated() ;
			
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
