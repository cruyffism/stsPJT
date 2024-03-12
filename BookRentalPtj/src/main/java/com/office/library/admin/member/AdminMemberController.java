package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/*import org.springframework.web.bind.annotation.RequestMethod;*/
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	//회원 가입
	/* @RequestMapping(value = "/createAccountForm", method= RequestMethod.GET) */
	//@RequestMapping("/createAccountForm")
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
	}
	
	/* 회원 가입 확인 */
	//@RequestMapping(value = "/createAccountConfirm", method= RequestMethod.POST)
	@PostMapping("/createAccountConfirm") // 입력 된 데이터를 view로 보낼 때 @PostMapping 사용
	public String createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberController] createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok"; //성공 시 주소 

		int result = adminMemberService.createAccountConfirm(adminMemberVo);
		
		if(result <= 0)
			nextPage = "admin/member/create_account_ng"; // 실패 시 주소 
		
		return nextPage; // 리턴값은 같지만 성공 여부에 따라 경로가 위 두 개로 갈림!

		//return null;
	}
	
	//로그인
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		
		String nextPage = "admin/member/login_form";
		
		return nextPage;
	}
	
	// 클릭 한 번에 왕복이 이루어짐(컨트롤러>>서비스>>다오>>서비스>>컨트롤러)
	//로그인 확인(관리자 로그인 인증)
	@PostMapping("/loginConfirm")	//프론트(login_form.jsp)에서 넘어온 값을 adminMemberVo(name="a_m_id",name="a_m_pw")라는 매개변수로 받음   
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) { //이 매개변수를 adminMemberVo에 담아서 loginConfirm 메서드를 호출 할 때 같이 보낸다 !
		System.out.println("[AdminMemberController] loginConfirm()");
		
		String nextPage = "admin/member/login_ok";
																			//매개변수를 쓰는 이유는 이렇게 가져다 쓰려고! 
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo); // AdminMemberService의 리턴값인 loginAdminMemberVo를 담은 것
		
		if (loginedAdminMemberVo == null) {
			nextPage = "admin/member/login_ng";
		} else {
			session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo); // 로그인 정보 저장.. (저장할 데이터의 이름, 실제 데이터)
			session.setMaxInactiveInterval(60*30); // 세선 유효기간 
			
		}
		
		return nextPage; // 리턴값은 같지만 성공 여부에 따라 경로가 위 두 개로 갈림!
	}
	
	//로그아웃 확인
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		
		String nextPage = "redirect:/admin";
		
		//session.removeAttribute("loginedAdminMemberVo");
		session.invalidate();
		
		return nextPage;
	}
	
	//관리자 목록(model 사용)
	/*
	 * @RequestMapping(value = "/listupAdmin", method = RequestMethod.GET) public
	 * String listupAdmin(Model model) {
	 * System.out.println("[AdminMemberController] modifyAccountConfirm()");
	 * 
	 * String nextPage = "admin/member/listup_admins";
	 * 
	 * List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
	 * 
	 * model.addAttribute("adminMemberVos", adminMemberVos);
	 * 
	 * return nextPage; }
	 */
	
	//관리자 목록(ModelandView 사용)
	@RequestMapping(value = "/listupAdmin", method = RequestMethod.GET) 
	public ModelAndView listupAdmin() {
		
		System.out.println("[AdminMemberController] modifyAccountConfirm()");
		
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
		
		ModelAndView modelAndView = new ModelAndView(); // ModelAndView 객체를 생성
		modelAndView.setViewName(nextPage);             // ModelAndView에 view를 설정
		modelAndView.addObject("adminMemberVos", adminMemberVos); // ModelAndView에 데이터를 추가
		
		return modelAndView; // ModelAndView를 반환
	}
	
	//관리자 승인
	//@RequestMapping(value = "/setAdminApproval", method = RequestMethod.GET)
	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberController] setAdminApproval()");
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
		
	}
}
