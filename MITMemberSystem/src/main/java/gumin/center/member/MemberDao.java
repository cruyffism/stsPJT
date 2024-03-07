package gumin.center.member;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	
		List<MemberVo> list = new ArrayList<MemberVo>(); 
		
		/* MemberVo member1 = new MemberVo(); */
		
	public void insertMember (MemberVo memberVo) {
		System.out.println("[MemberDao] insertMember()");
		
		System.out.println("m_id " + memberVo.getM_id());
		System.out.println("m_pw: " + memberVo.getM_pw());
		System.out.println("m_mail: " + memberVo.getM_mail());
		System.out.println("m_phone: " + memberVo.getM_phone());
		
		list.add(memberVo);// 새로운 회원 정보 추가
	}
		
	public MemberVo checkMember(MemberVo memberVo) {
		
		MemberVo signInedMember = null;
		
		for(MemberVo vo : list) {
			if(vo.getM_id().equals(memberVo.getM_id())) {
				signInedMember = vo;
			} 

		}
		
		if(signInedMember != null && memberVo.getM_pw().equals(signInedMember.getM_pw()))
			return signInedMember;
		else 
			return null;
		
	}
	
}
