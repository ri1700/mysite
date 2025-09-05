package com.bit2025.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2025.mysite.exception.GuestbookRepositoryException;
import com.bit2025.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private DataSource dataSource;
	
	public List<GuestbookVo> findAll() throws GuestbookRepositoryException {
		List<GuestbookVo> result = new ArrayList<>();
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select id, name, message, date_format(reg_date, '%Y-%m-%d %h:%i:%s') from guestbook order by reg_date desc");	
			ResultSet rs = pstmt.executeQuery();
		) {
			while(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String message = rs.getString(3);
				String regDate = rs.getString(4);
				
				GuestbookVo vo = new GuestbookVo();
				vo.setId(id);
				vo.setName(name);
				vo.setMessage(message);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		} 
		
		return result;
	}

	public int insert(GuestbookVo vo) {
		int count = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("insert into guestbook values(null, ?, ?, ?, now())");
		) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		} 
		
		return count;		
	}

	public int deleteByIdAndPassword(Long id, String password) {
		int count = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where id=? and password=?");
		) {
			pstmt.setLong(1, id);
			pstmt.setString(2, password);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		} 
		
		return count;		
	}
}