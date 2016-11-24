/*
 * ...added since Part 5.Security.
 *    
 */
package com.example.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/*
 * ...Writing a UserDetails service
 *    A common point of integration with security is to define a UserDetailsService. 
 *    This is the way to connect your user’s data store into a Spring Security interface. 
 *    Spring Security needs a way to look up users for security checks, and this is the bridge. 
 *    security 를 통합하는 공통점은 UserDetailService 를 정의하는 것임.
 *    이것은 당신의 사용자 데이터 store 를 Spring Security interface 안으로 연결하는 방법임.
 *    Spring Security 는 사용자에 대한 보안 점검을 찾는 수단이 필요한데, 
 *    UserDetailService 를 지정하는 것이 연결하는 교량임.
 */
@Component
public class SpringDataJpaUserDetailsService implements UserDetailsService {
	
	private final AccessManagerRepository repository;
	
/*
 * ...Because you have a ManagerRepository, there is no need to write any SQL or JPA expressions
 *    to fetch this needed data. 
 *    In this class, it is autowired by constructor injection.
 *    AccessManagerRepository 를 가지고 있으므로, 데이터를 조회하기 위한 어떠한 SQL 또는 JPA 표현을 
 *    쓸 필요가 없음.
 *    이 클래스에서는, 생성자 주입에 의해 autowired 됨.
 *    
 */
	@Autowired
	public SpringDataJpaUserDetailsService(AccessManagerRepository repository) {
		this.repository = repository;
	}


/*
 * ...SpringDataJpaUserDetailsService implements Spring Security’s UserDetailsService. 
 *    The interface has one method: loadUserByUsername(). 
 *    This method is meant to return a UserDetails object so Spring Security can interrogate the user’s information.
 *    SpringDataJpaUserDetailsService 는 Spring Security’s UserDetailsService 인터페이스를 구현함.
 *    UserDetailsService 인터페이스는 하나의 loadUserByUsername() 메서드를 가짐.
 *    이 메서드는 UserDetails 객체를 반환해서 Spring Security 가 사용자 정보를 조사할 수 있음.
 * 
 */
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
/*
 * ...loadUserByUsername() taps into the custom finder you write a moment ago, findByName(). 
 *    It then populates a Spring Security User instance, which implements the UserDetails interface. 
 *    You are also using Spring Securiy’s AuthorityUtils to transition from an array of 
 *    string-based roles into a Java List of GrantedAuthority.
 *    loadUserByUsername() 는 AccessManagerRepository.findByName(name) 를 활용함.
 *    그리고 나서  Spring Security 의 UserDetails 인터페이스를 구현하는 User 인자를 남김.
 *    Spring Securiy 의 AuthorityUtils 을 사용여 스트링 기반의 role 을 Java 의 List 형태의
 *    GrantedAuthority 로 변환함.
 *    		
 */
		AccessManager manager = this.repository.findByName(name);
		
		return new User(manager.getName()
						, manager.getPassword()
						, AuthorityUtils.createAuthorityList(manager.getRoles()));
	}

}
