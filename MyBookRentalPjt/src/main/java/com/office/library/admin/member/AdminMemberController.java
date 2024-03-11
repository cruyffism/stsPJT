package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired //AdminMemberService는 빈 객체로 생성 되어 있으므로 의존 객체 자동 주입 방법으로 AdminMemberService를 생성
	AdminMemberService adminMemberService;
	
	//회원가입
	//@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		
		String nextPage = "admin/member/create_account_form"; // jsp 주소
		
		return nextPage;
	}
	
	//회원가입 확인
	//@RequestMapping(value = "createAccountConfrim", method = RequestMethod.POST)
	@PostMapping("/createAccountConfirm")
	public String createAccountConfrim(AdminMemberVo adminMemberVo) {
		
		String nextPage = "/admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if(result <= 0) {
			nextPage = "/admin/member/create_account_ng";
		}
		
		return nextPage;
	}
}
