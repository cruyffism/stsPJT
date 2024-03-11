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
	
	public boolean isAdminMember(String a_m_id) {
		System.out.println("[AdminMemberDao] isAdminMember()");
		
		String sql = "SELECT COUNT(*) FROM tbl_admin_member"
				+ " WHERE a_m_id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id); // sql문, sql 결과값의 타입, ?에 들어갈 변수
		
		/*
		 * if (result > 0) return true; else return false;
		 */
		
		
		return result > 0 ? true : false;
	}
	
	 public int insertAdminAccount(AdminMemberVo adminMemberVo) {
			System.out.println("[AdminMemberDao] insertAdminAccount()");
			
			List<String> args = new ArrayList<String>(); // args라는 변수명으로 스트링타입을 리스트타입으로 지정
			
			String sql =  "INSERT INTO tbl_admin_member(";
			
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
