package com.bit2025.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.bit2025.mysite.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handler(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception e) throws Exception {
		
		//1. 로깅 (logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		//2. 요청 구분
		// json 요청: request header accept: application/json (o)
		// html 요청: request header accept: application/json (x)
		String accept = request.getHeader("accept");
		
		//3. json 응답
		if(accept.matches(".*application/json.*")) {
			JsonResult jsonResult = JsonResult.fail(e instanceof NoHandlerFoundException ? "Unknown API URL" : errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			
			return;
		}

		//4. HTML 응답 (종료)
		if(e instanceof NoHandlerFoundException || e instanceof NoResourceFoundException) { // 404 에러 페이지
			request
				.getRequestDispatcher("/WEB-INF/views/errors/404.jsp")
				.forward(request, response);
		} else { // 500 에러 페이지
			request.setAttribute("errors", errors);
			request
				.getRequestDispatcher("/WEB-INF/views/errors/exception.jsp")
				.forward(request, response);
		}
	}
}
