package com.office.library.user.member;

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
}
