package org.zerock.xweb;
//...379p.

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



/*
 * ...346p.REST(Representational State Transfer) : 하나의 URI는 하나의 고유한 리소스를
 * ...대표하도록 설계된다는 개념.
 * ...REST방식은 특정한 URI는 반드시 그에 상응하는 데이터 자체라는 것을 의미함.
 * ...예) '/zboard/123' : 게시물중 123번이라는 고유한 의미를 가지도록 설계하고,
 * ...                    이에 대한 처리는 GET, POST방식과 같이 추가적인 정보를
 * ...                    통해서 결정함.
 * ...최근 Open API에서 많이 사용되면서 REST방식으로 제공되는 외부 연결 URI를 REST API라함.
 * ...REST방식의 서비스 제공이 가능한 것을 'Restful'하다고 표현함.
 * ...
 * ...스프링4~ : @RestController 사용함.
 * ...           JSP와 같은 뷰를 만드는것이 목적이 아닌 REST방식의 데이터 처리를 위해 사용함.
 * 
 * ...스프링3~ : @ResponseBody를 지원하면서 본격적인 REST방식의 처리를 지원함.
 * ...
 * ...~스프링3 : Content Negotiation...을 이용해서 처리함.
 *               스프링3까지는 주로 @Controller만을 사용하고, 화면처리를 담당하는
 *               JSP가 아닌 데이터 자체를 서비스하려면 해당 메서드나 리턴타입에
 *               '@ResponseBody'를 추가하는 형태로 작성했음.
 *               과거에는 개발자가 직접 MIME타입을 지정하고, 해당 데이터를 만드는 방식이었음.
 * 
 * ...369p.REST방식을 이용하려면 우선 컨트롤러를 먼저 작성하고, 그에 맞는 URI를
 * ...결정하는 것이 첫단계임.
 * ...URI						|	전송방식	|	설명
 *  /replies/ + JSON데이터		|	POST		|	새로운 댓글 추가.
 *  /replies/all/123			|	GET			|	'게시물123'번의 모든 댓글 리스트 조회.
 *  /replies/456 + JSON데이터	|	PUT/PATCH	|	'댓글456'번의 수정.
 *  /replies/456				|	DELETE		|	'댓글456번'의 삭제.
 *  
 *  ...395p. @RestController를 이용하는 경우에는 데이터만 만들어서 전송하므로,
 *  ...화면처리에 개발이 집중이 됨.
 *  ...앱에서는 HTTP방식으로 데이터를 주고받는 라이브러리등을 활용해서 처리함.
 *  ...웹에서는 Ajax를 이용해서 처리함.
 */
@RestController
@RequestMapping("/zjson")
public class ReactJsonController {

	private static final Logger logger = LoggerFactory.getLogger(ReactJsonController.class);
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ResponseEntity<String> indexGET() {
	
		logger.info("indexGET called ...........");
				
		ResponseEntity<String> entity = null;
		String zmessage = "Merry Christmas^_____^!!!";
		try {
			logger.info("indexGET :: " + zmessage);	
			entity = new ResponseEntity<String>(zmessage, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.info("Error ...........");
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}	
	
}
