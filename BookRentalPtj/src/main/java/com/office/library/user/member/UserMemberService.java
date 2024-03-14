package com.office.library.user.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {
	
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	UserMemberDao userMemberDao;
	
	//회원 가입 처리 메서드
	public int createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] createAccountConfirm()");
		
		boolean isMember = userMemberDao.isUserMember(userMemberVo.getU_m_id()); //기존에 회원 정보 있는지 체크 >> false이면 기존에 회원 정보 없는거
		
		if(!isMember) { // 기존에 회원정보가 없으면 if문은 true이므로 
			int result = userMemberDao.insertUserAccount(userMemberVo); //회원 정보 저장, UserMemberDao의 리턴값인 result를 담은 것.
			
			if( result > 0)
				return USER_ACCOUNT_CREATE_SUCCESS; // 1 저장이 잘 됐을때
			else 
				return USER_ACCOUNT_CREATE_FAIL; // -1 저장 실패
		} else {
			return USER_ACCOUNT_ALREADY_EXIST; // 0 회원 정보 있을때
		}
			
	}
	
}


