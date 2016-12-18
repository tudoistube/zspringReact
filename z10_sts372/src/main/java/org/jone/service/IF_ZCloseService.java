package org.jone.service;
//...과제24-2.J. /z09_sts372/src/main/java/org/jone/service/IF_ZCloseService.java 생성.

import java.util.List;

import org.jone.zdomain.Close009VO;
import org.jone.zdomain.EmpVO;

public interface IF_ZCloseService {
	  /*public void insert(BoardVO vo) throws Exception;

	  public BoardVO read(Integer bno) throws Exception;

	  public void update(BoardVO vo) throws Exception;

	  public void delete(Integer bno) throws Exception;*/

	  public List<Close009VO> listAll_close009() throws Exception;	
}
