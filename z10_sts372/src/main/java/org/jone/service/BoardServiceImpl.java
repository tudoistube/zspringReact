package org.jone.service;
//...과제22-8.H. 비즈니스영역(Service계층)구현 : 2. 서비스 패키지 및 BoardService 인터페이스 및 객체 생성.
//...187p.
import java.util.List;

import javax.inject.Inject;

import org.jone.zdao.IF_BoardDAO;
import org.jone.zdomain.BoardVO;
import org.springframework.stereotype.Service;

//...@Service가 스프링의 빈으로 인식하게 함. root-context.xml::Beans Graph 확인할 것.
@Service
public class BoardServiceImpl implements IF_BoardService {

	@Inject
	private IF_BoardDAO dao;

	@Override
	public void insert(BoardVO board) throws Exception {
		dao.insert(board);
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
	    return dao.read(bno);
	}

	@Override
	public void update(BoardVO board) throws Exception {
	    dao.update(board);
	}

	@Override
	public void delete(Integer bno) throws Exception {
	    dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

}
