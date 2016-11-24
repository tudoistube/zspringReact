package com.example.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 * ...This class is marked with Spring’s @Component annotation 
 *    so that it is automatically picked up by @SpringBootApplication.
 * ...It implements Spring Boot’s CommandLineRunner so that it gets run 
 *    after all the beans are created and registered.
 * 
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

/*...before :
 	private final EmployeeRepository repository;  
...after : Part 5. */	
	private final EmployeeRepository employees;
	
	private final AccessManagerRepository managers; //...added since Part 5.

	/*
	 * ...It uses constructor injection and autowiring to get 
	 *    Spring Data’s automatically created EmployeeRepository.
	 */
	@Autowired
	
/*...before :
    public DatabaseLoader(EmployeeRepository repository) {
     	this.repository = repository;  
...after : Part 5. */
	public DatabaseLoader(EmployeeRepository employeeRepository
			              , AccessManagerRepository managerRepository) {
		this.employees = employeeRepository;
		this.managers = managerRepository;
	}

	/*
	 * ...The run() method is invoked with command line arguments, 
	 *    loading up your data.
	 */
/*...before :
	public void run(String... strings) throws Exception {
		this.repository.save(new Employee("Frodo2", "Baggins2", "ring bearer2"));
		this.repository.save(new Employee("2Do", "2Be", "2DoIs2Be"));		
		this.repository.save(new Employee("Gandalf", "the Grey", "wizard"));
		this.repository.save(new Employee("Samwise", "Gamgee", "gardener"));
		this.repository.save(new Employee("Meriadoc", "Brandybuck", "pony rider"));		
		this.repository.save(new Employee("한글11", "한글22", "한글33"));
		this.repository.save(new Employee("汉语1", "汉语2", "汉语3"));		
	}
...after : Part 5~.
...Pre-loading manager data
   Loading managers and linking employees to these managers is rather straight forward:
   
...The one wrinkle is that Spring Security is active with access rules in full force 
   when this loader runs.
   Spring Security 는 이 loader 가 실행될 때, 모든 힘을 다해서 접근 규칙을 가지면서
   active 됨.
   Thus to save employee data, you must use Spring Security’s setAuthentication() API 
   to authenticate this loader with the proper name and role.
   그렇게 employee 데이터를 저장하기 위해서, 당신은 이 loader 가 적절한 이름과 role 을 
   가지고 권한을 획득하게 하는 Spring Security 의 setAuthentication() API 를 반드시 
   사용해야 함. 
   At the end, the security context is cleared out.
   결국, security context 는 청소됨. 
   
*/	
	@Override
	public void run(String... strings) throws Exception {

		AccessManager greg = this.managers.save(new AccessManager("greg", "turnquist",
							"ROLE_MANAGER"));
		AccessManager oliver = this.managers.save(new AccessManager("oliver", "gierke",
							"ROLE_MANAGER"));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("greg", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.employees.save(new Employee("Frodo", "Baggins", "ring bearer", greg));
		this.employees.save(new Employee("Bilbo", "Baggins", "burglar", greg));
		this.employees.save(new Employee("Gandalf", "the Grey", "wizard", greg));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.employees.save(new Employee("Samwise", "Gamgee", "gardener", oliver));
		this.employees.save(new Employee("Merry", "Brandybuck", "pony rider", oliver));
		this.employees.save(new Employee("Peregrin", "Took", "pipe smoker", oliver));

		SecurityContextHolder.clearContext();
	}

}










