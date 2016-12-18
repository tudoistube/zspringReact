package org.jone.web;
//...과제22-3.F. JSON데이터를 생성하는 샘플.
import org.jone.zdomain.ProductVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController5 {

	@RequestMapping("/doJSON")
	public @ResponseBody ProductVO doJSON(){
		
		ProductVO vo = new ProductVO("zweb.샘플상품",30000);
		
		return vo;
		
	}
	
}
