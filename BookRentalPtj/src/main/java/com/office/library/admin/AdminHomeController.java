package com.office.library.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin") //admin에대한 요청을 이 컨트롤러에서 담당한다.  
public class AdminHomeController {
	@RequestMapping(value= {"", "/"}, method = RequestMethod.GET)
	public String home() {
		System.out.println("[AdminHomeController] home()");
		
		String nextPage = "admin/home";
		
		return nextPage;
	}

}
