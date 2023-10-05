package com.kh.spring.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;


@Repository
public class BoardDao {

	public int selectListCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("boardMapper.selectListCount");
	}
	
	public ArrayList<Board> selectList(SqlSessionTemplate sqlSession, PageInfo pi) {
		
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit(); // 몇개씩 건너뛸건지
		int limit = pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return (ArrayList)sqlSession.selectList("boardMapper.selectList", null, rowBounds); // 자릿수위해.. 넘길거 없어도 반드시 null 작성

	}
	
	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.insert("boardMapper.insertBoard", b);
	}
	
	public int increaseCount(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.increaseCount", boardNo);
	}
	
	public Board selectBoard(SqlSessionTemplate sqlSession, int boardNo) { // 반환형 헷갈리면 controller에서 최종으로 받고자 하는 자료형과 맞추면 됨 Board b = ..
		return sqlSession.selectOne("boardMapper.selectBoard", boardNo);
	}
	
	public int deleteBoard(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.update("boardMapper.deleteBoard", boardNo);
	}
	
	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.update("boardMapper.updateBoard", b);
	}
	
}
