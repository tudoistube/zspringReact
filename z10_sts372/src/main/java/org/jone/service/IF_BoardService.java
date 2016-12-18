package org.jone.service;
//...과제22-8.H. 비즈니스영역(Service계층)구현 : 2. 서비스 패키지 및 BoardService 인터페이스 및 객체 생성.
import java.util.List;

import org.jone.zdomain.BoardVO;

//...186p.
public interface IF_BoardService {

	  public void insert(BoardVO board) throws Exception;

	  public BoardVO read(Integer bno) throws Exception;

	  public void update(BoardVO board) throws Exception;

	  public void delete(Integer bno) throws Exception;

	  public List<BoardVO> listAll() throws Exception;
	
	
}
