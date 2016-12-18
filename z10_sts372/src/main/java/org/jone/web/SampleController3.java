package org.jone.web;
//...과제22-3.D. 만들어진 결과 데이터를 전달하는 샘플.
import org.jone.zdomain.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController3 {
	private static final Logger logger = 
			LoggerFactory.getLogger(SampleController3.class);
	
	/*
	 * ...만들어진 결과 데이터를 전달하는 샘플.
	 * ...Model은 뷰에 데이터를 전달하는 Map처럼 Key, Value로 구성된 컨테이너 상자 역할.
	 * ...model.addAttribute(전달할데이터);
	 * ...http://localhost:8080/web/doD
	 */
	@RequestMapping("/doD")
	public String doD(Model model){
		
		//make sample data
		ProductVO product = new ProductVO("Sample Product", 12345);
		
		logger.info("doD");
	
		//...addAttribute(객체)처럼 객체이름을 별도로 지정하지 않은 경우, 자동으로 저장되는
		//...객체 클래스명의 앞글자를 소문자로 처리한 이름이 객체이름이 됨.
		//...여기서는 productVO가 객체명이 됨.
		model.addAttribute(product);
		
		return "productDetail";
		
	}	
}
