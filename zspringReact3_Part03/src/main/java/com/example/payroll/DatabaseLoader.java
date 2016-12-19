package com.example.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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

	private final EmployeeRepository repository;

	/*
	 * ...It uses constructor injection and autowiring to get
	 *    Spring Data’s automatically created EmployeeRepository.
	 */
	@Autowired
	public DatabaseLoader(EmployeeRepository repository) {
		this.repository = repository;
	}

	/*
	 * ...The run() method is invoked with command line arguments,
	 *    loading up your data.
	 *
	 */
	@Override
	public void run(String... strings) throws Exception {
		this.repository.save(new Employee("Frodo2", "Baggins2", "ring bearer2"));
		this.repository.save(new Employee("2Do", "2Be", "2DoIs2Be"));
		this.repository.save(new Employee("Gandalf", "the Grey", "wizard"));
		this.repository.save(new Employee("Samwise", "Gamgee", "gardener"));
		this.repository.save(new Employee("Meriadoc", "Brandybuck", "pony rider"));
		this.repository.save(new Employee("JoyWins", "Yes", "Direction wins over Speed!"));
		this.repository.save(new Employee("한글11", "한글22", "한글33"));
		this.repository.save(new Employee("汉语1", "汉语2", "汉语3"));
	}

}
