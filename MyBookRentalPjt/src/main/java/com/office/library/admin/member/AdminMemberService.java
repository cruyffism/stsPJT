package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //스프링 컨테이너에 빈 객체로 생성되기 위해 명시
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_CREATE_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDao adminMemberDao;
	
	//회원가입 처리 메서드
	public int createAccountConfirm(AdminMemberVo adminMemberVo) {
		
		boolean isMember =  adminMemberDao.isAdminMember(adminMemberVo.getA_m_id()); // 중복 아이디 체크 isMember가 true이면 이미 사용중인 아이디이고 false이면 사용 가능한 아이디!
		
		if(!isMember) { //여기선 !isMember가 true이면  AdminMemberDao의 insertAdminAccount 메서드를 실행한다.
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);
			
			if (result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS; // 1: 성공
			else 
				return ADMIN_ACCOUNT_CREATE_FAIL; // -1: 실패 (DB INSERT 실패) 
		} else {
			return ADMIN_ACCOUNT_CREATE_EXIST; // 0: 실패 (중복 아이디)
		}
	}

}
