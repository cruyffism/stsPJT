package com.company.hello.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	@RequestMapping("/signUp") //요청 url매핑
	public String signUp() {
		return "sign_up";
	}
	
	@RequestMapping("/signIn") //요청 url매핑
	public String signIn() {
		return "sign_in";
	}
}
