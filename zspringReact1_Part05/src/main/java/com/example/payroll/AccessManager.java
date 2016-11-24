/*
 * ...added since Part 5.Security.
 *    
 */
package com.example.payroll;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

//@Data

/*
 * ...There is a key thing to keep in mind when designing your security layer. 
 *    Secure the right bits of data (like passwords) and do NOT let them get 
 *    printed to console, into logs, or exported via JSON serialization.
 *    @ToString(exclude = "password") ensures that the Lombok-generated toString() 
 *    method will NOT print out the password.
 *    lombok 을 이용하여 password 가 출력되지 않게 함(본 실습에서는 사용하지 않음).
 *    
 */
//@ToString(exclude="password")
@Entity
public class AccessManager {
	
//...PASSWORD_ENCODER is the means to encrypt new passwords or 
//   to take password inputs and encrypt them before comparison.
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
//...id, name, password, and roles define the parameters needed to restrict access.
	private @Id @GeneratedValue Long id;

	private String name;

//...@JsonIgnore applied to the password field protects from Jackson serializing this field.
//  password 는 JSON 형태로 출력되지 않게 함.	
	private @JsonIgnore String password;

	private String[] roles;

	protected AccessManager() {}	
	

	public AccessManager(String name, String password, String... roles) {
		super();
		this.name = name;
		this.setPassword(password);
		this.roles = roles;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

//...The customized setPassword() ensures that passwords are never stored in the clear.
	public void setPassword(String password) {
		this.password = PASSWORD_ENCODER.encode(password);
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

//...password 는 출력되지 않게 함.	
	@Override
	public String toString() {
		return "AccessManager [id=" + id + ", name=" + name + ", roles=" + Arrays.toString(roles) + "]";
	}
	
	
}

