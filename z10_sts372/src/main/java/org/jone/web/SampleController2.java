package org.jone.web;
//...과제22-3.C. String 리턴타입 샘플.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
/***
 * http://localhost:8080/zweb/doC?msg="2Be"
 * @author Administrator
 *
 */
@Controller
public class SampleController2 {

	private static final Logger logger = 
			LoggerFactory.getLogger(SampleController2.class);
	
	/*
	 * ...String 리턴타입인 경우 : /WEB-INF/views/'String 리턴값'.jsp(suffix) 를 실행함.
	 * ...http://localhost:8080/web/doC?msg=%222Be%22
	 */
	@RequestMapping("doC")
	public String doC( @ModelAttribute("msg") String msg){
		
		logger.info("zweb.doC called..............................");
		
		return "result";
		
	}	
	
}
