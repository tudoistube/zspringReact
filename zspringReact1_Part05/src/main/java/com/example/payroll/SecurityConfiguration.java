/*
 * ...added since Part 5.Security.
 *    
 */
package com.example.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
/*
 * ...@EnableWebSecurity tells Spring Boot to drop its autoconfigured security policy 
 *    and use this one instead. 
 *    For quick demos, autoconfigured security is okay. 
 *    But for anything real, you should write the policy yourself.
 *    @EnableWebSecurity 는 Spring Boot 가 자신의 자동 설정된 security 정책을 포기하고
 *    이것을 대신 사용하도록 함.
 */
@EnableWebSecurity
/*
 * ...@EnableGlobalMethodSecurity turns on method-level security with Spring Security’s 
 *    sophisticated @Pre and @Post annotations.
 *    @EnableGlobalMethodSecurity 는 Spring Security 의 복잡한 @Pre 와 @Post 어노테이션을 가진
 *    메서드 레벨의 보안을 켬.
 * 
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
/*
 * ...It extends WebSecurityConfigurerAdapter, a handy base class to write policy.
 *    SecurityConfiguration 클래스는 정책을 서술하는 편리한 기반 클래스인 WebSecurityConfigurerAdapter 를
 *    상속함.
 */
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

/*
 * ...It autowired the SpringDataJpaUserDetailsService by field inject and then plugs it in  
 *    via the configure(AuthenticationManagerBuilder) method. 
 *    The PASSWORD_ENCODER from  Manager is also setup.	
 */
	@Autowired
	private SpringDataJpaUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(this.userDetailsService)
			.passwordEncoder(AccessManager.PASSWORD_ENCODER);
	}

/*
 * ...The pivotal security policy is written in pure Java with the configure(HttpSecurity).
 *    중심축이 되는 보안 정책은 configure(HttpSecurity) 를 가진 순수 Java 로 기술되어 있음.
 *    
 * ...The security policy says to authorize all requests using the access rules defined earlier.
 *    보안 정책은 미리 정의된 접근 규칙을 사용하는 모든 규칙에 권한을 부여하도록 함.
 *    
 */
	@Override
	protected void configure(HttpSecurity http)  throws Exception {
/*
 * ...The paths listed in antMatchers() are granted unconditional access 
 *    since there is no reason to block static web resources.
 *    antMatchers() 안에 나열된 경로는 정적 웹 자원의 접근을 막을 이유가 없으므로 
 *    무조건적인 접근을 허용함.
 *    
 *    Anything that doesn’t match that falls into anyRequest().authenticated() meaning
 *    it requires authentication.
 *    anyRequest().authenticated() 에 매칭되지 않는 모든 것은 권한을 요구함.
 *    
 *    With those access rules setup, Spring Security is told to use form-based authentication, 
 *    defaulting to "/" upon success, and to grant access to the login page.
 *    그러한 접근 규칙을 세워서, Spring Security 는 성공인 경우 기본 설정된 '/'와 login 페이지에 대한
 *    접근을 허용하는 form 기반의 권한을 사용하도록 됨.
 *    
 *    BASIC login is also configured with CSRF disabled. 
 *    This is mostly for demonstrations and not recommended for production systems without careful analysis.
 *    Logout is configured to take the user to "/".
 *    기본 login 은 또한 CSRF 비활성화되도록 설정되어 있음.
 *    이것은 대부분 시연용이고, 조심스러운 분석이 없는 운영 시스템에는 추천되지 않음.
 *    https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)
 *    (HTTPS 가 아닌 HTTP 에 대해 권한을 부여하는 어떠한 방식도 비밀이 누설될 위험이 있고,
 *    CSRF 는 비활성화되면 BASIC 과 CURL 을 더 쉽게 상호작용하도록 함).
 *    Logout 은 사용자를 '/'로 이끌도록 설정됨.
 *     		
 */
		
		http
		.authorizeRequests()
			.antMatchers("/built/**", "/main.css").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.defaultSuccessUrl("/", true)
			.permitAll()
			.and()
		.httpBasic()
			.and()
		.csrf().disable()
		.logout()
			.logoutSuccessUrl("/");
	}	
	
}
