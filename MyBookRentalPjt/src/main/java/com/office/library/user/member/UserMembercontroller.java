package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
public class UserMembercontroller {

	@Autowired
	UserMemberService userMemberService;

	// 회원가입
	@GetMapping("/createAccountForm")
	public String createAccountForm() {

		String nextPage = "user/member/create_account_form";

		return nextPage;
	}

	// 회원가입 확인
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(UserMemberVo userMemberVo) {

		String nextPage = "user/member/create_account_ok";

		int result = userMemberService.createAccountConfirm(userMemberVo);

		if (result <= 0)
			nextPage = "user/member/create_account_ng";

		return nextPage;
	}

	// 로그인
	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "user/member/login_form";

		return nextPage;
	}

	// 로그인 확인
	@PostMapping("/loginConfirm")
	public String loginConfirm(UserMemberVo userMemberVo, HttpSession session) {

		String nextPage = "user/member/login_ok";

		UserMemberVo loginedUserMemberVo = userMemberService.loginConfirm(userMemberVo);

		if (loginedUserMemberVo == null) {
			nextPage = "user/member/login_ng";
		} else {
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60 * 30);
		}

		return nextPage;

	}

	// 계정 수정
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {

		String nextPage = "user/member/modify_account_form";

		UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
		if (loginedUserMemberVo == null)
			nextPage = "redirect:/user/member/loginForm";

		return nextPage;

	}

	// 회원 정보 수정 확인
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVo userMemberVo, HttpSession session) {

		String nextPage = "user/member/modify_account_ok";

		int result = userMemberService.modifyAccountConfirm(userMemberVo);

		if (result > 0) {
			UserMemberVo loginedUserMemberVo = userMemberService.getLoginedUserMemberVo(userMemberVo.getU_m_no());

			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo);
			session.setMaxInactiveInterval(60 * 30);

		} else {
			nextPage = "user/member/modify_account_ng";

		}

		return nextPage;

	}

	// 로그아웃 확인
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {

		String nextPage = "redirect:/";

//		session.removeAttribute("loginedUserMemberVo");
		session.invalidate();

		return nextPage;
	}
	
	//비밀번호 찾기 
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		
		String nextPage = "user/member/find_password_form";
		
		return nextPage;
		
	}
	
	//비밀번호 찾기 확인
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberVo userMemberVo) {
		
		String nextPage = "user/member/find_password_ok";
		
		int result = userMemberService.findPasswordConfirm(userMemberVo);
		
		if( result <= 0)
			
			nextPage = "user/member/find_password_ng";
		
		return nextPage; 
	}

}
