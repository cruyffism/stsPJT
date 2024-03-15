package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
public class UserMemberController {
	
	@Autowired
	UserMemberService userMemberService;
	
	//회원가입
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[UserMemberController] createAccountForm()");
		
		String nextPage = "user/member/create_account_form";
		
		return nextPage;
	}
	
	//회원가입 확인
	@PostMapping("/createAccountConfirm") // 입력 된 데이터를 view로 보낼 때 @PostMapping 사용
	public String createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberController] createAccountConfirm()");
		
		String nextPage = "user/member/create_account_ok"; //성공 시 주소
		
		int result = userMemberService.createAccountConfirm(userMemberVo);
		
		if (result <= 0)
			nextPage = "user/member/create_account_ng"; // 실패 시 주소
		
		return nextPage; 
	}
	
	//로그인
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[UserMemberController] loginForm()");
		
		String nextPage = "user/member/login_form";
		
		return nextPage;
	}
	
	//로그인 확인
	@PostMapping("/loginConfirm") //프론트(login_form.jsp)에서 넘어온 값을 userMemberVo(name="u_m_id",name="u_m_pw")라는 매개변수로 받음
	public String loginConfirm(UserMemberVo userMemberVo, HttpSession session) { //이 매개변수를 adminMemberVo에 담아서 loginConfirm 메서드를 호출 할 때 같이 보낸다 !
		System.out.println("[UserMemberController] loginConfirm()");             // 로그인 성공 시 세션에 로그인 정보 추가를 위해 HttpSession도 매개변수로 받는다.
		
		String nextPage = "user/member/login_ok";
		
		UserMemberVo loginedUserMemberVo = userMemberService.loginConfirm(userMemberVo); // userMemberService의 리턴값인 userMemberVo를 담은 것
		
		if(loginedUserMemberVo == null) {
			nextPage = "user/member/login_ng";
		} else {
			session.setAttribute("loginedUserMemberVo", loginedUserMemberVo); //로그인 정보 저장.. (저장할 데이터의 이름, 실제 데이터)
			session.setMaxInactiveInterval(60 * 30); //세션 유효기간
		}
		
		return nextPage; // 리턴값은 같지만 성공 여부에 따라 경로가 위 두 개로 갈림!
	}
	
    // 계정 수정
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountForm()");
		
		String nextPage = "user/member/modify_account_form";
		
		UserMemberVo loginedUserMemberVo = (UserMemberVo) session.getAttribute("loginedUserMemberVo");
		if (loginedUserMemberVo == null)
			nextPage = "redirect:/user/member/loginForm";
		
		return nextPage;
		
	}
	
	/*
	 * 회원 정보 수정 확인
	 */
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVo userMemberVo, HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountConfirm()");
		
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
	
	/*
	 * 로그아웃 확인
	 */
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[UserMemberController] logoutConfirm()");
		
		String nextPage = "redirect:/";
		
//		session.removeAttribute("loginedUserMemberVo");
		session.invalidate();
		
		return nextPage;
		
	}
	
	/*
	 * 비밀번호 찾기
	 */
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		System.out.println("[UserMemberController] findPasswordForm()");
		
		String nextPage = "user/member/find_password_form";
		
		return nextPage;
		
	}
	
	/*
	 * 비밀번호 찾기 확인
	 */
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberController] findPasswordConfirm()");
		
		String nextPage = "user/member/find_password_ok";
		
		int result = userMemberService.findPasswordConfirm(userMemberVo);
		
		if (result <= 0)
			nextPage = "user/member/find_password_ng";
		
		return nextPage;
		
	}
	

}
