package com.office.library.admin.member;

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
		
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id()); //기존에 회원 정보 있는지 체크
		
		if (!isMember) { // 기존에 회원정보가 없으면 
			int result = adminMemberDao.insertAdminAccount(adminMemberVo); //회원 정보 저장
			
			if(result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS; // 1 저장이 잘 됐을때
			else 				
				return ADMIN_ACCOUNT_CREATE_FAIL; // -1 저장 실패
		} else {
			return  ADMIN_ACCOUNT_ALREADY_EXIST; // 0 회원 정보 있을때
		}
		
		//return 0;
	}
	
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {
		System.out.println("[AdminMemberService] loginConfirm()");
		
		AdminMemberVo loginAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo);
		
		if (loginAdminMemberVo != null)
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");
		
		else 
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		
		return loginAdminMemberVo;
		
	}

}
