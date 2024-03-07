package com.company.hello.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component //의존 객체 자동 주입 (빈 생성)
public class MemberDao {
	
	private Map<String, MemberVo> memberDB = new HashMap<String, MemberVo>();
	
	public void insertMember(MemberVo memberVo) {
		System.out.println("[MemberDao] insertMember()");
		
		System.out.println("m_id " + memberVo.getM_id());
		System.out.println("m_pw: " + memberVo.getM_pw());
		System.out.println("m_mail: " + memberVo.getM_mail());
		System.out.println("m_phone: " + memberVo.getM_phone());
		
		memberDB.put(memberVo.getM_id(), memberVo); // 새로운 회원 정보 추가
		printAllMember();
	}
	
	private void printAllMember() {
		System.out.println("[MemberDao] printAllMember()");
		
		Set<String> keys = memberDB.keySet();
		Iterator<String> iterator = keys.iterator();
		
		while (iterator.hasNext()) {
			String Key = iterator.next();
			MemberVo memberVo = memberDB.get(Key);
			
			System.out.println("m_id " + memberVo.getM_id());
			System.out.println("m_pw: " + memberVo.getM_pw());
			System.out.println("m_mail: " + memberVo.getM_mail());
			System.out.println("m_phone: " + memberVo.getM_phone());
		}
	
	}
	
	public MemberVo selectMember(MemberVo memberVo) {
		System.out.println("[MemberDao] selectMember()");
		
		MemberVo signInedMember = memberDB.get(memberVo.getM_id());
		
		if(signInedMember != null && memberVo.getM_pw().equals(signInedMember.getM_pw()))
			return signInedMember;
		else 
			return null;
	}

}