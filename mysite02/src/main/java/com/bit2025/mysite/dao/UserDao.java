package com.bit2025.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bit2025.mysite.vo.UserVo;

public class UserDao {

	public int insert(UserVo vo) {
		int result = 0;
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement(
				" insert" +
				"   into user(name, email, password, gender, join_date)" + 
				" values (?, ?, password(?), ?, current_date)"
			);
		){
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			 System.out.println("error:" + e);
		}
		
		return result;
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo result = null;
		
		try (
			Connection con = getConnection();
			PreparedStatement pstmt = con.prepareStatement("select id, name from user where email = ? and password = password(?)");
		) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				result = new UserVo();
				result.setId(id);
				result.setName(name);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			 System.out.println("error:" + e);
		}
		
		return result;
	}	

	
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url  = "jdbc:mariadb://192.168.0.183:3306/webdb";
			con =  DriverManager.getConnection (url, "webdb", "webdb");
		} catch(ClassNotFoundException ex) {
			System.out.println("Driver Class Not Found");
		}
		
		return con;		
	}


}