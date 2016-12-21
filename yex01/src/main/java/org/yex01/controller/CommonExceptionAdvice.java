package org.yex01.controller;
//...233p.

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/*
 * ...233p.Spring MVC를 사용할때 Controller에서 Exception을 처리하는 방법.
 * ...1. @ExceptionHandler 이용.
 * ...2. @ControllerAdvice 이용.
 * ...		공통의 Exception 처리 전용 객체를 사용하는 방법.
 * ...		호출되는 메서드에서 발생된 Exception을 모두 처리.
 * ...		@ControllerAdvice클래스의 메서드는 발생한 Exception객체의 타입만을 파라미터로
 * ...		사용가능하고, 일반Controller처럼 Model을 파라미터로 지원하지 않으므로
 * ...		직접 ModelAndView타입을 사용하는 형태로 작성해야 함.
 * ...		2.1. 클래스에 @ControllerAdvice 처리.
 * ...		2.2. 각 메서드에 @ExceptionHandler 이용해서 적절한 타입의 Exception 처리.
 * ...		ex) http://localhost:8080/z/zboard/read?bno=36 강제로 예외를 발생시켜봄.
 * ...3. @ResponseStatus 이용한 Http상태 코드 처리.
 */
//@ControllerAdvice
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

	//@ExceptionHandler(Exception.class)
	public String common(Exception e) {

		logger.info("CommonExceptionAdvice : " + e.toString());

		return "/yboard/error_common";
	}
	
	@ExceptionHandler(Exception.class)
	private ModelAndView errorModelAndView(Exception ex) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/yboard/error_common");
		modelAndView.addObject("exception", ex);

		return modelAndView;
	}

}
