package com.office.library.admin.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminMemberDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//중복 아이디 체크 메서드
	public boolean isAdminMember(String a_m_id) {
		
		String sql =  "SELECT COUNT(*) FROM tbl_my_admin_member "
					+ "WHERE a_m_id = ?";
		
		//queryForObject()가 1을 반환 시 관리자가 입력한 아이디는 이미 사용중이라서 회원가입 불가
		// 0이면 사용중인 아이디가 아니므로 회원가입 가능
		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);
		
		if (result > 0) 
			return true;
		else 
			return false;
	}
	
	public int insertAdminAccount(AdminMemberVo adminMemberVo) {
		
		List<String> args = new ArrayList<String>();
		
		String sql =  "INSERT INTO tbl_my_admin_member(";
		
		   if (adminMemberVo.getA_m_id().equals("super admin")) {
			   sql += "a_m_approval, ";
			   args.add("1");
		   }
		   
		   sql += "a_m_id, "; // +=을 사용해서 문자열을 이어붙임!
		   args.add(adminMemberVo.getA_m_id());
		   
		   sql += "a_m_pw, ";
		   args.add(passwordEncoder.encode(adminMemberVo.getA_m_pw())); // pw 암호화 메서드
		   
		   sql += "a_m_name, ";
		   args.add(adminMemberVo.getA_m_name());
		   
		   sql += "a_m_gender, ";
		   args.add(adminMemberVo.getA_m_gender());
		   
		   sql += "a_m_part, ";
		   args.add(adminMemberVo.getA_m_part());
		   
		   sql += "a_m_position, ";
		   args.add(adminMemberVo.getA_m_position());
		   
		   sql += "a_m_mail, ";
		   args.add(adminMemberVo.getA_m_mail());
		   
		   sql += "a_m_phone, ";
		   args.add(adminMemberVo.getA_m_phone());
		   
		   sql += "a_m_reg_date, a_m_mod_date) ";
		   
		   if (adminMemberVo.getA_m_id().equals("super admin"))  // super admin과 일반관리자에게 차이를 두는 것
			   sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())"; //"super admin"이면 a_m_approval 값을 1로 설정
		   else 
			   sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())"; //"super admin"이 아닌 경우에는 a_m_approval 열을 제외
		   
	int result = -1; //오류 혹은 쿼리가 실행되지 않은 경우를 구분하기 위한 초기값 설정
	
	try {
		
		result = jdbcTemplate.update(sql, args.toArray());
		
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return result;
	
	}
}
