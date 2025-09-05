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
import com.bit2025.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

	@Autowired
	private DataSource dataSource;

	public int insert(BoardVo boardVo) {
		int result = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(
				"insert into board values (null, ?, ?, 0, now(), (select ifnull(max(g_no), 0) + 1 from board a), 1, 0, ?)"
			); 	
			PreparedStatement pstmt2 = conn.prepareStatement(
				"insert into board values (null, ?, ?, 0, now(), ?, ?, ?, ?)"
			); 	
		) {
			if(boardVo.getGroupNo() == null) {
				pstmt1.setString(1, boardVo.getTitle());
				pstmt1.setString(2, boardVo.getContents());
				pstmt1.setLong(3, boardVo.getUserId());
				
				result = pstmt1.executeUpdate();
			} else {
				pstmt2.setString(1, boardVo.getTitle());
				pstmt2.setString(2, boardVo.getContents());
				pstmt2.setInt(3, boardVo.getGroupNo());
				pstmt2.setInt(4, boardVo.getOrderNo());
				pstmt2.setInt(5, boardVo.getDepth());
				pstmt2.setLong(6, boardVo.getUserId());
				
				result = pstmt2.executeUpdate();
			}	
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public List<BoardVo> findAllByPageAndKeword(String keyword, Integer page, Integer size) {
		List<BoardVo> result = new ArrayList<>();
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(
				"   select a.id," +
				"          a.title," +
				"          a.hit," +
				"          date_format(a.reg_date, '%Y-%m-%d %p %h:%i:%s') as regDate," +
				"          a.depth," +
				"          b.name as userName," +
				"          a.user_id as userId" +
				"     from board a, user b" +
				"    where a.user_id = b.id" +
				" order by g_no desc, o_no asc" +
				"    limit ?, ?");
			PreparedStatement pstmt2 = conn.prepareStatement(
				"   select a.id," +
				"          a.title," +
				"          a.hit," +
				"          date_format(a.reg_date, '%Y-%m-%d %p %h:%i:%s') as regDate," +
				"          a.depth," +
				"          b.name as userName," +
				"          a.user_id as userId" +
			    "     from board a, user b" +
			    "    where a.user_id = b.id" +
			    "      and (title like ? or contents like ?)" +
			    " order by g_no desc, o_no asc" +
			    "    limit ?, ?");
		) {	
			ResultSet rs = null;
			
			if("".equals(keyword)) {
				pstmt1.setInt(1, (page - 1) * size);
				pstmt1.setInt(2, size);

				rs = pstmt1.executeQuery();
			} else {
				pstmt2.setString(1, "%" + keyword + "%");
				pstmt2.setString(2, "%" + keyword + "%");
				pstmt2.setInt(3, (page - 1) * size);
				pstmt2.setInt(4, size);

				rs = pstmt2.executeQuery();
			}

			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				int depth = rs.getInt(5);
				String userName = rs.getString(6);
				Long userId = rs.getLong(7);

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserName(userName);
				vo.setUserId(userId);
				
				result.add(vo);
			}
			
			rs.close();
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.toString());
		}

		return result;
	}

	public int update(BoardVo boardVo) {
		int result = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
				"update board set title=?, contents=? where id=? and user_id=?"
			);
		) {
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getId());
			pstmt.setLong(4, boardVo.getUserId());
				
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public int delete(Long id, Long userId) {
		int result = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
				"delete from board where id = ? and user_id = ?"
			);
		) {
			pstmt.setLong(1, id);
			pstmt.setLong(2, userId);
				
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public BoardVo findById(Long boardId) {
		BoardVo result = null;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
				"select	id, title, contents, g_no, o_no, depth, user_id from board where id = ?"
			);
		) {
			pstmt.setLong(1, boardId);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int groupNo = rs.getInt(4);
				int orderNo = rs.getInt(5);
				int depth = rs.getInt(6);
				Long userId = rs.getLong(7);
				
				result = new BoardVo();
				result.setId(id);
				result.setTitle(title);
				result.setContents(contents);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
				result.setUserId(userId);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public BoardVo findByIdAndUserId(Long boardId, Long userid) {
		BoardVo result = null;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
				"select	id, title, contents from board where id = ? and user_id = ?"
			);
		) {
			pstmt.setLong(1, boardId);
			pstmt.setLong(2, userid);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				
				result = new BoardVo();
				result.setId(id);
				result.setTitle(title);
				result.setContents(contents);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public int updateHit(Long id) {
		int result = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
				"update board set hit = hit + 1 where id=?"
			);
		) {
			pstmt.setLong(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public int updateOrderNo(Integer groupNo, Integer orderNo) {
		int result = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
				"update board set o_no = o_no + 1 where g_no = ? and o_no >= ?"
			);
		) {
			pstmt.setInt(1, groupNo);
			pstmt.setInt(2, orderNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}

	public int getTotalCount(String keyword) {
		int result = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt1 = conn.prepareStatement(
				"select count(*) from board"
			);
			PreparedStatement pstmt2 = conn.prepareStatement(
				"select count(*) from board where title like ? or contents like ?"
			);
		) {
			ResultSet rs = null;
			
			if("".equals(keyword)) {
				rs = pstmt1.executeQuery();
			} else {
				pstmt2.setString(1, "%" + keyword + "%");
				pstmt2.setString(2, "%" + keyword + "%");
				
				rs = pstmt2.executeQuery();
			}
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return result;
	}
}