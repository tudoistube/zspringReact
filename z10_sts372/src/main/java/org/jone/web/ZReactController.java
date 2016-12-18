package org.jone.web;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/zreact/*")
public class ZReactController {
	private static final Logger logger = LoggerFactory.getLogger(ZReactController.class);
	
	@RequestMapping(value = "/zindex", method = RequestMethod.GET)
	public void zindexGET(Locale locale, Model model) {
		logger.info("zindexGET starts...");	}	
	
}
