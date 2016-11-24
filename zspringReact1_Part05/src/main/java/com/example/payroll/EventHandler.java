/*
 * ...added since Part 4.Event using WebSocket.
 *    Repositories 에서 일어나는 액션에 근거해서 Spring Data REST 는 8가지
 *    이벤트를 생성시킴.
 *    http://docs.spring.io/spring-data/rest/docs/current/reference/html/#events
 *    다음 코드는 어떻게 이러한 이벤트들을 구독하는지를 보여줌. 
 * ...In essense, you are listening for create, update, and delete events, 
 *    and after they are completed, sending notice of them to all clients.
 *    본질적으로, 생성, 업데이트 그리고 삭제가 처리된 후 해당 이벤트들의 통지를 
 *    모든 클라이언트에게 보내서 당신은 해당 이벤트를 듣게 됨.
 *    It’s also possible to intercept such operations BEFORE they happen, 
 *    and perhaps log them, block them for some reason, or decorate the domain objects 
 *    with extra information. 
 *    그러한 작업들이 발생하기 전에 가로채어서 기록하고, 막거나 추가 정보를 가진 도메인 
 *    객체를 장식하는 것 역시 가능함.
 *    
 */
package com.example.payroll;

import static com.example.payroll.WebSocketConfiguration.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
//...@RepositoryEventHandler(Employee.class) : flags this class to trap events 
//   based on employees.
//   @RepositoryEventHandler(Employee.class) : employees 에 근거하여 이벤트들을
//   덫을 놓아 잡는 클래스로 구분함.
@RepositoryEventHandler(Employee.class)
public class EventHandler {

//...SimpMessagingTemplate and EntityLinks are autowired from the application context.
//   SimpMessagingTemplate 와 EntityLinks 는 application context 로 부터 자동으로 연결됨.
	private final SimpMessagingTemplate websocket;

//...EntityLinks comes with several utility methods to programmatically find the paths of 
//   various resources, whether single or for collections.	
//   EntityLinks 는 단일 또는 컬렉션의 다양한 자원들의 경로를 프로그래밍적으로 찾을 수 있는
//   여러개의 도구 메서드를 가지고 있음.	
	private final EntityLinks entityLinks;

	@Autowired
	public EventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
		this.websocket = websocket;
		this.entityLinks = entityLinks;
	}

//...The @HandleXYZ annotations flag the methods that need to listen to. 
//   These methods must be public.
//   @HandleAfterCreate, @HandleAfterDelete, @HandleAfterSave 어노테이션은
//   이벤트들을 듣도록 메서드를 구분함.
//   이러한 메서드들은 반드시 public 이어야 함.
//	
//...Each of these handler methods invokes SimpMessagingTemplate.convertAndSend() 
//   to transmit a message over the WebSocket.
//   각각의 이러한 메서드 핸들러들은 SimpMessagingTemplate.convertAndSend() 메서드를
//   작동시켜서 WebSocket 으로 메시지를 전송하게 함.
//   This is a pub-sub approach so that one message is relayed to every attached consumer.
//   이것은 하나의 메시지가 모든 붙어있는 사용자에게 전달되도록 함.
//
//...The route of each message is different, allowing multiple messages to be sent 
//   to distinct receivers on the client while only needing one open WebSocket, 
//   a resource-efficient approach.
//   자원 효율적인 접근법인 하나의 WebSocket 만 여는 것을 필요로 하면서	
//   복수의 메시지들이 클라이언트의 명확한 수신자에게 보내질 수 있도록 
//   각 메시지의 경로는 다름.   	
	@HandleAfterCreate
	public void newEmployee(Employee employee) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/newEmployee", getPath(employee));
	}

	@HandleAfterDelete
	public void deleteEmployee(Employee employee) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/deleteEmployee", getPath(employee));
	}

	@HandleAfterSave
	public void updateEmployee(Employee employee) {
		this.websocket.convertAndSend(
				MESSAGE_PREFIX + "/updateEmployee", getPath(employee));
	}

	/**
	 * Take an {@link Employee} and get the URI using Spring Data REST's {@link EntityLinks}.
	 *
	 * @param employee
	 */
//...getPath() uses Spring Data REST’s EntityLinks to look up the path for a given class type and id.
//   getPath() 는 Spring Data REST 의 EntityLinks 를 사용해서 주어진 클래스 타입과 id 를 찾음.
//   To serve the client’s needs, this Link object is converted to a Java URI with its path extracted.
//   클라이언트의 필요를 돕기 위해서, 이 Link 객체는 추출된 경로를 가진 Java URI 로 변환됨.
	private String getPath(Employee employee) {
		return this.entityLinks.linkForSingleResource(employee.getClass(),
				employee.getId()).toUri().getPath();
	}

}
