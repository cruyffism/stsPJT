package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/*import org.springframework.web.bind.annotation.RequestMethod;*/

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	/* @RequestMapping(value = "/createAccountForm", method= RequestMethod.GET) */
	//@RequestMapping("/createAccountForm")
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
	}
	
	/* 회원 가입 확인 */
	/*
	 * @RequestMapping(value = "/createAccountConfirm", method= RequestMethod.POST)
	 */
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok"; //성공 시 주소 
		
		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/member/create_account_ng"; // 실패 시 주소 
		
		return nextPage; // 리턴값은 같지만 성공 여부에 따라 경로가 위 두 개로 갈림!

		//return null;
	}

}
