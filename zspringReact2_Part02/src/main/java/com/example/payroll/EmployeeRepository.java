package com.example.payroll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/*
 * ...What is difference between CrudRepository and JpaRepository interfaces in Spring Data JPA?
 * http://stackoverflow.com/questions/14014086/what-is-difference-between-crudrepository-and-jparepository-interfaces-in-spring
 */
//XXXpublic interface EmployeeRepository extends CrudRepository<Employee, Long> { //...~Part01.
/*
 * ...PagingAndSortingRepository : 페이지 크기를 설정하는 기타 옵션을 추가하고, 
 *    네비게이셔널한 링크를 추가해서 페이지에서 페이지로 점프할 수 있음. 
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> { //...Part02~.
	
}
