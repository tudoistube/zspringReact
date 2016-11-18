
package com.example.payroll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Greg Turnquist
 */

@Data
/*
 * ...@Entity is a JPA annotation that denotes the whole class for storage 
 *    in a relational table.
 * ...@Id and @GeneratedValue are JPA annotations to note the primary key 
 *    and that is generated automatically when needed.
 * ...@Data is a Project Lombok annotation to autogenerate getters, setters, 
 *    constructors, toString, hash, equals, and other things. 
 *    It cuts down on the boilerplate.
 *    
 */
@Entity
public class Employee {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String description;

/*
* ...@Version : 어떤 row 가 추가 또는 변경될 때마다 자동적으로 어떤 값을 저장
*    또는 갱신하게끔 함.
*    (컬렉션 자원이 아닌) 개별 자원을 가져올 때, Spring Data REST 는 자동적으로
*    ETag 응답 헤더를 이 필드값에 붙임.
*    https://tools.ietf.org/html/rfc7232#section-2.3 *
*
*/	
	private @Version @JsonIgnore Long version;
	
	private Employee() {}

	public Employee(String firstName, String lastName, String description) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}
}