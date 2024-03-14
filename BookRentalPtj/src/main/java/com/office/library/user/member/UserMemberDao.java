package com.office.library.user.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserMemberDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public boolean isUserMember(String u_m_id) {
		System.out.println("[UserMemberDao] isUserMember()");
		
		String sql = "SELECT COUNT(*) FROM tbl_user_member"
				   + " WHERE u_m_id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, u_m_id); // sql문, sql 결과값의 타입, ?에 들어갈 변수
		
		return result > 0 ? true : false;
	}

}
