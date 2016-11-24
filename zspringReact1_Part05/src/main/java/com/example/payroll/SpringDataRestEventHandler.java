/*
 * ...added since Part 5.Security.
 *    
 */
package com.example.payroll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
/*
 * ...@RepositoryEventHandler(Employee.class) flags this event handler as only applied to Employee objects.
 *    @RepositoryEventHandler(Employee.class) 는 이 이벤트 핸들러가 오직 Employee 객체에만 적용됨을 알림.      
 *    
 */
@RepositoryEventHandler(Employee.class)
public class SpringDataRestEventHandler {
	
	private final AccessManagerRepository managerRepository;

	@Autowired
	public SpringDataRestEventHandler(AccessManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
	}

/*
 * ...The @HandleBeforeCreate annotation gives you a chance to alter the incoming Employee record 
 *    before it gets written to the database.
 *    @HandleBeforeCreate 어노테이션은 당신이 들어오는 Employee 레코드가 DB 에 기록되기 전에 
 *    이를 고칠 수 있는 기회를 줌.
 *    
 */
	@HandleBeforeCreate
	public void applyUserInformationUsingSecurityContext(Employee employee) {

/*
 * ...you lookup the current user’s security context to get the user’s name.
 *    현재 사용자의 security context 를 찾아서 사용자 이름을 가져옴. 
 *    Then look up the associated manager using findByName() and apply it to the manager.
 *    그리고 나서 findByName() 을 사용해서 관련된 manager 를 찾아서 manager 에 적용함.
 *    		
 */
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		
		AccessManager manager = this.managerRepository.findByName(name);
/*
 * ...There is a little extra glue code to create a new manager if he or she doesn’t exist in the system yet. 
 *    But that is mostly to support initialization of the database. 
 *    In a real production system, that code should be removed and instead depend on the DBAs or Security Ops team
 *    to properly maintain the user data store.
 *    만약 존재하지 않는 사용자일 때, 시연 목적의 마무리용 추가 코드로서 새로운 manager 를 생성함.
 *    하지만 대부분 DB 의 초기화 지원임.
 *    실제 운영 시스템에서는, 이러한 코드는 제거되어야 하고 
 *    DBA 또는 보안 운영팀이 적절히 사용자 데이터 저장을 관리해야 함.
 *      		
 */
		if (manager == null) {
			AccessManager newManager = new AccessManager();
			newManager.setName(name);
			newManager.setRoles(new String[]{"ROLE_MANAGER"});
			manager = this.managerRepository.save(newManager);
		}
		
		employee.setManager(manager);
		
	}	
	
}
