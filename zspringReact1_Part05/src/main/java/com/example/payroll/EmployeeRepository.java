package com.example.payroll;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

/*
 * ...What is difference between CrudRepository and JpaRepository interfaces in Spring Data JPA?
 * http://stackoverflow.com/questions/14014086/what-is-difference-between-crudrepository-and-jparepository-interfaces-in-spring
 */
//XXXpublic interface EmployeeRepository extends CrudRepository<Employee, Long> { //...~Part01.


// ...'ROLE_MANAGER' 역할을 가진 manager 만이 employee's data 를 변경할 수 있게 함.
@PreAuthorize("hasRole('ROLE_MANAGER')") //...added since Part 05.
/*
 * ...PagingAndSortingRepository : 페이지 크기를 설정하는 기타 옵션을 추가하고, 
 *    네비게이셔널한 링크를 추가해서 페이지에서 페이지로 점프할 수 있음. 
 */
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

/*
 * ...added since Part 05.
 * ...On save(), either the employee’s manager is null (initial creation of a new employee 
 *    when no manager has been assigned), or the employee’s manager’s name matches 
 *    the currently authenticated user’s name.
 *    (어떠한 manager 가 지정되지 않는 때의 새로운 employee 의 초기 생성 상태인) employee 의 
 *    manager 가 null 이거나 employee 의 manager 의 이름이 현재 권한을 획득한 사용자의 이름과
 *    일치할 때 save() 가 실행됨.
 * ...Spring Security Reference   
 *    http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#el-access
 * ...It comes with a handy "?." property navigator to handle null checks.
 *    '?.' 속성은 null 을 체크를 취급함.
 *    It’s also important to note using the @Param(…​) on the arguments to link HTTP operations 
 *    with the methods.
 *    아규먼트에 대한 @Param(...) 는 method 를 가진 HTTP 작업을 연결하는데 사용함.
 * 
 */
	@Override
	@PreAuthorize("#employee?.manager == null or #employee?.manager?.name == authentication?.name")
	Employee save(@Param("employee") Employee employee);

/*
 * ...added since Part 05.
 *    On delete(), the method either has access to the employee, or in the event it only has an id, 
 *    then it must find the employeeRepository in the application context, perform a findOne(id), 
 *    and then check the manager against the currently authenticated user.
 *    delete() 는 application context 에서 employeeRepository 를 찾아서 findOne(id) 를 수행해서
 *    manager 가 현재 권한을 획득한 사용자인지 확인함. 
 *    
 */
	@Override
	@PreAuthorize("@employeeRepository.findOne(#id)?.manager?.name == authentication?.name")
	void delete(@Param("id") Long id);

	@Override
	@PreAuthorize("#employee?.manager?.name == authentication?.name")
	void delete(@Param("employee") Employee employee);

}
























