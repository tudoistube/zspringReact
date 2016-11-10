package com.example.payroll;

import org.springframework.data.repository.CrudRepository;

/*
 * ...What is difference between CrudRepository and JpaRepository interfaces in Spring Data JPA?
 * http://stackoverflow.com/questions/14014086/what-is-difference-between-crudrepository-and-jparepository-interfaces-in-spring
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
