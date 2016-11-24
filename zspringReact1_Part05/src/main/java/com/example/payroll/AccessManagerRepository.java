/*
 * ...added since Part 5.Security.
 *    
 */
package com.example.payroll;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/*
 * ...Spring Data REST, by default, will export any repository it finds. 
 *    You do NOT want this repository exposed for REST operations! 
 *    Apply the @RepositoryRestResource(exported = false) annotation to block it from export. 
 *    This prevents the repository from being served up as well as any metadata.
 *    기본적으로 Spring Data REST 는 자신이 발견한 repository 는 export 함.
 *    보안과 같은 이유로, 이 repository 를 REST 작업에 노출하지 않고자 원하므로, 
 *    @RepositoryRestResource(exported = false) 어노테이션을 적용하여 export 를 막음.
 *    
 */
@RepositoryRestResource(exported = false)
/*
 * ...Instead of extending the usual CrudRepository, you don’t need so many methods. 
 *    Instead, you need to save data (which is also used for updates) and you need to 
 *    look up existing users. 
 *    Hence, you can use Spring Data Common’s minimal Repository marker interface. 
 *    It comes with no predefined operations.
 *    많은 메서드를 사용할 필요가 없으므로 CrudRespository 를 사용하지 않고, 가장 작은
 *    Repository 를 사용함.
 *    
 */
public interface AccessManagerRepository extends Repository<AccessManager, Long> {

	AccessManager save(AccessManager manager);
	
	AccessManager findByName(String name);
	
}
