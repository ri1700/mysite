package com.bit2025.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2025.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}

	public int deleteByIdAndPassword(Long id, String password) {
		return sqlSession.delete("guestbook.deleteByIdAndPassword", Map.of("id", id, "password", password));
	}
}