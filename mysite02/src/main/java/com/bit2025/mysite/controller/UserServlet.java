package com.bit2025.mysite.controller;

import java.io.IOException;

import com.bit2025.mysite.dao.UserDao;
import com.bit2025.mysite.vo.UserVo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");
		
		if("joinform".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp").forward(request, response);
		} else if("join".equals(action)) {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			new UserDao().insert(vo);
			
			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
		} else if("joinsuccess".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp").forward(request, response);
		} else if("loginform".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp").forward(request, response);
		} else if("login".equals(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			UserVo authUser = new UserDao().findByEmailAndPassword(email, password);
			
			if(authUser == null) {
				request.setAttribute("email", email);
				request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp").forward(request, response);
				return;
			}
			
			// 로그인처리 (세션처리)
			HttpSession session = request.getSession(true);
			session.setAttribute("authUser", authUser);
			
			response.sendRedirect(request.getContextPath());
		} else if("logout".equals(action)) {
			HttpSession session = request.getSession();
			if(session != null) {
				// 로그아웃 처리
				session.removeAttribute("authUser");
				session.invalidate();
			}
			
			response.sendRedirect(request.getContextPath());
		} else if("updateform".equals(action)) {
			// Access Control
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}
			///////////////////////////
			
			Long id = authUser.getId();
			// UserVo userVo = new UserDao().findById(id);
			// request.setAttribute("userVo", userVo);

			request.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp").forward(request, response);
		} else if("update".equals(action)) {
			// Access Control
			HttpSession session = request.getSession();
			if(session == null) {
				response.sendRedirect(request.getContextPath());
				return;
			}
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			if(authUser == null) {
				
				response.sendRedirect(request.getContextPath());
				return;
			}
			
			///////////////////////////
			
			String name = request.getParameter("name");
			String password = request.getParameter("parameter");
			String gender = request.getParameter("gender");

		} else {
			response.sendRedirect(request.getContextPath());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}