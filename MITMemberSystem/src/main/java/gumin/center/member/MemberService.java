package gumin.center.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	MemberDao memberDao; 

	public int signUpConfirm(MemberVo memberVo) {
		System.out.println("[MemberService] signUpConfirm()");	
		
		System.out.println("m_id: " + memberVo.getM_id());
		System.out.println("m_pw: " + memberVo.getM_pw());
		System.out.println("m_mail: " + memberVo.getM_mail());
		System.out.println("m_phone: " + memberVo.getM_phone());
		
		memberDao.insertMember(memberVo);  //memberVo를 매개변수로 전달함
		
		return 0;
	}
	
	public MemberVo signInConfirm(MemberVo memberVo) {
		System.out.println("[MemberService] signInConfirm()");
		
		MemberVo signInedMember = memberDao.checkMember(memberVo); 
		return signInedMember; //로그인한 회원 정보 변환
	}

}
