package org.jone.zdao;
//...과제24-2.G. /z09_sts372/src/main/java/org/jone/zdao/IF_CloseDAO.java 생성.
import java.util.List;

import org.jone.zdomain.BoardVO;
import org.jone.zdomain.Close009VO;
import org.jone.zdomain.EmpVO;

/***
 * DAO = Persistence 패키지.
 * @author Administrator
 *
 */
public interface IF_ZCloseDAO {

	  /*public void insert(BoardVO vo) throws Exception;

	  public BoardVO read(Integer bno) throws Exception;

	  public void update(BoardVO vo) throws Exception;

	  public void delete(Integer bno) throws Exception;*/

	  public List<Close009VO> listAll_close009() throws Exception;	
	
}
