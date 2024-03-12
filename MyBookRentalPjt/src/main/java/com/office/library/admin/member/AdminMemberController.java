package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	
	//로그인 
	@GetMapping("/loginForm")
	public String loginForm() {
		String nextPage = "admin/member/login_form";
		
		return nextPage;
	}
	
	//로그인 확인(관리자 로그인 인증)
	@PostMapping("/loginConfirm") //프론트(login_form.jsp)에서 넘어온 값을 adminMemberVo(name="a_m_id",name="a_m_pw")라는 매개변수로 받음
	public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session) { //이 매개변수를 adminMemberVo에 담아서 loginConfirm 메서드를 호출 할 때 같이 보낸다 !
		
		String nextPage = "admin/member/login_ok";
		                                                                 //매개변수를 쓰는 이유는 이렇게 가져다 쓰려고! 
		AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo); // AdminMemberService의 리턴값인 loginAdminMemberVo를 담은 것
		
		if(loginedAdminMemberVo == null) {
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
		
		String nextPage = "redirect:/admin"; //리다이렉트
		
		session.invalidate(); //세션 무효화
		
		return nextPage;
	}
	
	//관리자 목록
	@GetMapping("/listupAdmin")
	public ModelAndView listupAdmin(Model model) {
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin(); 
		
		//모델은 서버의 데이터를 뷰에 전달하는 역할을 한다.  (서버)AdminMemberController >> (클라이언트)listup_admins.jsp
		ModelAndView modelAndView = new ModelAndView(); // ModelAndView 객체를 생성
		modelAndView.setViewName(nextPage); // ModelAndView에 뷰를 설정
		modelAndView.addObject("adminMemberVos", adminMemberVos); //ModelAndView에 데이터를 추가한다.
		
		return modelAndView;
		
	}
	
	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no); // adminMemberService꺼 메서드 호출
		
		return nextPage;
		
	}
	
}
