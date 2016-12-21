package org.yex01.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/zreact/*")
public class ReactController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReactController.class);	

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void indexGET() throws Exception {
		logger.info("indexGet called ...........");

	}	
	
}
