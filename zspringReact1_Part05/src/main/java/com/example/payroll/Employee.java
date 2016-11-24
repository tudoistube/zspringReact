
package com.example.payroll;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import lombok.Data;

/**
 * @author Greg Turnquist
 */

//@Data
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
*    https://tools.ietf.org/html/rfc7232#section-2.3 
*
*/	
	private @Version @JsonIgnore Long version;
	
/*
 * ...associate employees with a manager. 
 *    In this domain, an employee can have one manager 
 *    while a manager can have multiple employees:
 *    The manager attribute is linked via JPA’s @ManyToOne. 
 *    Manager doesn’t need the @OneToMany 
 *    because you haven’t defined the need to look that up.
 *    이 도메인에서, 하나의 manager 는 여러개의 employees 를 가질 수 있는 반면에,
 *    하나의 employee 는 하나의 manager 를 가질 수 있음.
 * ...The manager attribute is linked via JPA’s @ManyToOne. 
 *    Manager doesn’t need the @OneToMany because you haven’t defined the need to 
 *    look that up.
 *    manager 속성은 JPA 의 @ManyToOne 으로 연결됨.
 *    	
 */
	private @ManyToOne AccessManager manager; //...added since Part 5.
	
	private Employee() {}



	public Employee(String firstName, String lastName, String description, AccessManager manager) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.manager = manager;//...added since Part 5.
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public AccessManager getManager() {
		return manager;
	}

	public void setManager(AccessManager manager) {
		this.manager = manager;
	}



	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", description="
				+ description + ", version=" + version + ", manager=" + manager + "]";
	}	
	
	
}