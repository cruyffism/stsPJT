package com.office.library.admin.member;

import java.util.List;

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
	
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {// AdminMemberController에서 보낸 매개변수를 받는다
		
		AdminMemberVo loginAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo); //AdminMemberDao의 리턴값인 adminMemberVos.get(0) or null을 담은 것
		
		if(loginAdminMemberVo != null) 
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");
		
		else 
			
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		
		return loginAdminMemberVo;
	}
	
	public List<AdminMemberVo> listupAdmin() { 
		return adminMemberDao.selectAdmins();
	}
	
	public void setAdminApproval(int a_m_no) {
		
		int result = adminMemberDao.updateAdminAccount(a_m_no);// Dao에 관리자 번호를 전달해서 a_m_approval 값을 0에서 1로 업데이트하게 한다.
	}
}
