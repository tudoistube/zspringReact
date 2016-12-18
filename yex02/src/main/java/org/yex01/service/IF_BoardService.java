package org.yex01.service;

import java.util.List;

import org.yex01.domain.BoardVO;
import org.yex01.domain.Criteria;
import org.yex01.domain.SearchCriteria;

public interface IF_BoardService {

	  public void insert(BoardVO board) throws Exception;

	  public BoardVO read(Integer bno) throws Exception;

	  public void update(BoardVO board) throws Exception;

	  public void delete(Integer bno) throws Exception;

	  public List<BoardVO> listAll() throws Exception;	

	  public List<BoardVO> listCriteria(Criteria cri) throws Exception;

	  //...280p. int listCountCriteria(Criteria cri)	  
	  public int countBno(Criteria cri) throws Exception;

	  //...331p.
	  public List<BoardVO> listSearchCriteria(SearchCriteria cri) 
	      throws Exception;

	  public int listSearchCount(SearchCriteria cri) throws Exception;
	  	  
	  
}
