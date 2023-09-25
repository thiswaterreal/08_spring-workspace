package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberDao;
import com.kh.spring.member.model.vo.Member;

//@Component
@Service
public class MemberServiceImpl implements MemberService {

	// 전역으로 (대신 타고 가보면 무조건 bean등록되어 있어야 함)
	@Autowired
	private MemberDao mDao;
	
	// 전역으로
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Override
	public Member loginMember(Member m) {
		
		Member loginMember = mDao.loginMember(sqlSession, m);
		return loginMember;
	}

	@Override
	public int insertMember(Member m) {
		return 0;
	}

	@Override
	public int updateMember(Member m) {
		return 0;
	}

	@Override
	public int deleteMember(String userId) {
		return 0;
	}

	@Override
	public int idCheck(String userId) {
		return 0;
	}

}
