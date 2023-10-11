package com.kh.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao bDao;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	
	/** 전체 게시글 수 조회 (페이징위해)
	 *
	 */
	@Override
	public int selectListCount() {
		return bDao.selectListCount(sqlSession);
	}

	
	/** 전체 게시글 리스트 조회
	 *
	 */
	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return bDao.selectList(sqlSession, pi);
	}

	
	/** 게시글 작성하기
	 *
	 */
	@Override
	public int insertBoard(Board b) {
		return bDao.insertBoard(sqlSession, b);
	}

	
	/** 게시글 상세 조회수 증가
	 *
	 */
	@Override
	public int increaseCount(int boardNo) {	// controller에서 bno 였지만 여기선 boardNo 해도 노상관. 자료형만 같으면 됨
		return bDao.increaseCount(sqlSession, boardNo);
	}

	
	/** 게시글 상세페이지 조회
	 *
	 */
	@Override
	public Board selectBoard(int boardNo) {
		return bDao.selectBoard(sqlSession, boardNo);
	}

	
	/** 게시글 상세페이지에서 삭제
	 *
	 */
	@Override
	public int deleteBoard(int boardNo) {
		return bDao.deleteBoard(sqlSession, boardNo);
	}

	@Override
	public int updateBoard(Board b) {
		return bDao.updateBoard(sqlSession, b);
	}

	@Override
	public ArrayList<Reply> selectReplyList(int boardNo) {
		return bDao.selectReplyList(sqlSession, boardNo);
	}

	@Override
	public int insertReply(Reply r) {
		return bDao.insertReply(sqlSession, r);
	}
	

}
