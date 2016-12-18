package org.jone.zdao;
//...과제22-8.C. /z09_sts372/src/main/java/org/jone/zdao/IF_BoardDAO.java 생성하기.
import java.util.List;

import org.jone.zdomain.BoardVO;

/***
 * DAO = Persistence 패키지.
 * @author Administrator
 *
 */
public interface IF_BoardDAO {

	  public void insert(BoardVO vo) throws Exception;

	  public BoardVO read(Integer bno) throws Exception;

	  public void update(BoardVO vo) throws Exception;

	  public void delete(Integer bno) throws Exception;

	  public List<BoardVO> listAll() throws Exception;	
	
}
