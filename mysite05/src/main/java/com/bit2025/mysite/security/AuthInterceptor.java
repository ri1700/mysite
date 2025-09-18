package com.bit2025.mysite.security;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bit2025.mysite.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1. Handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHttpRequestHandler 타입인 경우
			// DefaultServletHttpRequestHandler가 처리하는 경우(정적자원, /assets/**)
			return true;
		}
		
		// 2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. handlerMethod에서 @Auth 가져오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. 만약, handlerMethod에 @Auth가 없다면 Class(Type)에서 가져오기 
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		// 5. handlerMethod와 Class(Type) 둘 다에 @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		// 6. @Auth가 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 7. 권한(Authorization) 체크를 위해서 @Auth의 role("USER", "ADMIN") 가져오기
		String role = auth.role();

		// 8. @Auth의 role이 "USER"인 경우, authUser의 role은 "USER"이던 "ADMIN"이던 상관 없다.
		if("USER".equals(role)) {
			return true;
		}
		
		// 9. @Auth의 role이 "ADMIN"인 경우, authUser의 role은 "ADMIN"이어야 한다.
		if(!"ADMIN".equals(authUser.getRole())) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		// 10. 옳은 관리자 권한 [@Auth(role="ADMIN") && authUser.role == "ADMIN"]
		return true;
	}

}
