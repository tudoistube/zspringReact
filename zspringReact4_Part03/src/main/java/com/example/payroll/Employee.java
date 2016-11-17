
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
	
	private @Version @JsonIgnore Long version;

	private Employee() {}

	public Employee(String firstName, String lastName, String description) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
	}
}