package org.yex01.service;
//...375p.

import java.util.List;

import org.yex01.domain.Criteria;
import org.yex01.domain.ReplyVO;

public interface IF_ReplyService {

	public List<ReplyVO> selectReplies(Integer bno) throws Exception;

	public void insertReply(ReplyVO vo) throws Exception;

	public void updateReply(ReplyVO vo) throws Exception;

	public void deleteReply(Integer rno) throws Exception;

	//...392p.
	public List<ReplyVO> selectPageReplies(Integer bno, Criteria cri) throws Exception;

	public int countReplies(Integer bno) throws Exception;	
	
}
