package gumin.center.member;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	
		List<MemberVo> list = new ArrayList<MemberVo>(); 
	
	public void insertMember (MemberVo memberVo) {
		System.out.println("m_id " + memberVo.getM_id());
		System.out.println("m_pw: " + memberVo.getM_pw());
		System.out.println("m_mail: " + memberVo.getM_mail());
		System.out.println("m_phone: " + memberVo.getM_phone());

	}
	
}
