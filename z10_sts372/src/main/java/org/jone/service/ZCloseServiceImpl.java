package org.jone.service;
//...과제24-2.K. /z09_sts372/src/main/java/org/jone/service/ZCloseServiceImpl.java 생성.
//...187p.
import java.util.List;

import javax.inject.Inject;

import org.jone.zdao.IF_ZCloseDAO;
import org.jone.zdomain.Close009VO;
import org.jone.zdomain.EmpVO;
import org.springframework.stereotype.Service;

//...@Service가 스프링의 빈으로 인식하게 함. root-context.xml::Beans Graph 확인할 것.
@Service
public class ZCloseServiceImpl implements IF_ZCloseService {

	@Inject
	private IF_ZCloseDAO dao;	
	
	@Override
	public List<Close009VO> listAll_close009() throws Exception {
		// TODO Auto-generated method stub
		return dao.listAll_close009();
	}

}
