package org.jone.web;
//...과제22-3.B. void 리턴타입 샘플.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {

	private static final Logger logger = 
			LoggerFactory.getLogger(SampleController.class);
	
	/*
	 * ...void 리턴타입인 경우 : 현재 경로에 해당하는 JSP파일 실행함.
	 * ...http://localhost:8080/web/doA or doB
	 */
	@RequestMapping("doA")
	public void doA(){
		
		logger.info("zweb.doA called....................");
		
	}
	
	@RequestMapping("doB")
	public void doB(){
		
		logger.info("zweb.doB called....................");
		
	}	
	
	
}
