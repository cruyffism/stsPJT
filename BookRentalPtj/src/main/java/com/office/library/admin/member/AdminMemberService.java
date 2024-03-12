package com.office.library.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDao adminMemberDao;
	
	public int createAccountConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] createAccountConfirm()");
		
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id()); //기존에 회원 정보 있는지 체크 >> false이면 기존에 회원 정보 없는거
		
		if (!isMember) { // 기존에 회원정보가 없으면 if문은 true이므로 
			int result = adminMemberDao.insertAdminAccount(adminMemberVo); //회원 정보 저장, AdminMemberDao의 리턴값인 result를 담은 것.
			
			if(result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS; // 1 저장이 잘 됐을때
			else 				
				return ADMIN_ACCOUNT_CREATE_FAIL; // -1 저장 실패
		} else {
			return  ADMIN_ACCOUNT_ALREADY_EXIST; // 0 회원 정보 있을때
		}
		
		//return 0;
	}
	
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) { // AdminMemberController에서 보낸 매개변수를 받는다
		System.out.println("[AdminMemberService] loginConfirm()");
		
		AdminMemberVo loginAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo); //AdminMemberDao의 리턴값인 adminMemberVos.get(0) or null을 담은 것
		
		if (loginAdminMemberVo != null)
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");
		
		else 
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		
		return loginAdminMemberVo;
		
	}
	
	public List<AdminMemberVo> listupAdmin() {
		System.out.println("[AdminMemberService] listupAdmin()");
		
		return adminMemberDao.selectAdmins();
	}

}
