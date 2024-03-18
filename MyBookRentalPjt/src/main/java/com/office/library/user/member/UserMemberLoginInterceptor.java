package com.office.library.user.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class UserMemberLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		if (session != null) { // null이 아니면  true를 반환해서 Handler 컨트롤러를 실행!
			Object object = session.getAttribute("loginedUserMemberVo");
			
			if (object != null)
				return true;
			
		}
		//loginedUserMemberVo == null이면 로그인 화면 유도 
		response.sendRedirect(request.getContextPath() + "/user/member/loginForm");
		
		return false;
		
	}
	
}
