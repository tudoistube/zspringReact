package org.jone.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jone.service.IF_ZCloseService;
import org.jone.zdomain.Close009VO;
import org.jone.zdomain.EmpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/zclose/*")
public class ZCloseController {
	
	@Inject
	private IF_ZCloseService service;
	
	  private static final Logger logger = LoggerFactory.getLogger(ZCloseController.class);

	  @RequestMapping(value = "/listAll_close009", method = RequestMethod.GET)
	  public ModelAndView close009GET(Model model) throws Exception {
		  
		logger.info("listAll_close009 called....................!!");
		List<Close009VO> listAll = service.listAll_close009();
		
		//...S.과제24-2.N. 엑셀파일 생성뷰를 하나로 만들기.
		/*...가져와야 할 멤버변수들이 모두 private이어서 안됨.
		Class vo = EmpVO.class;
		Field[] voFields = vo.getFields();
		model.addAttribute("voFields", voFields);
		map.put("voFields", voFields);
		*/
		
		Field[] arrFields = EmpVO.class.getDeclaredFields();
		String[] arrStrFields = {"EMPNO", "ENAME", "JOB"};
		//...E.과제24-2.N. 엑셀파일 생성뷰를 하나로 만들기.			
	    
		// return a view which will be resolved by an excel view resolver
	    //return new ModelAndView("excelView_allInOne", "listAll", pList);
	    
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("excelView_close009");
	    mav.addObject("arrFields", arrFields);
	    mav.addObject("arrStrFields", arrStrFields);
	    mav.addObject("listAll", listAll);
	    mav.addObject("title", "excelView_close009"); //...제목만 다르게 준다.
	    
	    return mav;
	    
		}
	  
	  @RequestMapping(value = "/closeMain", method = RequestMethod.GET)
	  public void closeMainGET() throws Exception {
	    logger.info("closeMainGet called ...........");
	    
	  }	 
	  
	  @RequestMapping(value = "/closeMainReact", method = RequestMethod.GET)
	  public void closeMainReactGET() throws Exception {
	    logger.info("closeMainReactGet called ...........");
	    
	  }	 	  
}
