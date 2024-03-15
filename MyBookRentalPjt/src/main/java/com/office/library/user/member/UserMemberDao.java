package com.office.library.user.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMemberDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate; // db와 통신하기 위해 JdbcTemplate 자동 주입

	@Autowired
	PasswordEncoder passwordEncoder;

	public boolean isUserMember(String u_m_id) {
		System.out.println("[UserMemberDao] isUserMember()");

		String sql = "SELECT COUNT(*) FROM tbl_user_member" + " WHERE u_m_id = ?";

		int result = jdbcTemplate.queryForObject(sql, Integer.class, u_m_id); // sql문, sql 결과값의 타입, ?에 들어갈 변수

		return result > 0 ? true : false;
	}

	public int insertUserAccount(UserMemberVo userMemberVo) { // db에 신규 회원 추가
		System.out.println("[UserMemberDao] insertUserAccount()");

		String sql = "INSERT INTO tbl_user_member(u_m_id, " + "u_m_pw, " + "u_m_name, " + "u_m_gender, " + "u_m_mail, "
				+ "u_m_phone, " + "u_m_reg_date, " + "u_m_mod_date) VALUES(?, ?, ?, ?, ?, ?, NOW(), NOW())";

		int result = -1;

		try {
			result = jdbcTemplate.update(sql, userMemberVo.getU_m_id(),
					passwordEncoder.encode(userMemberVo.getU_m_pw()), userMemberVo.getU_m_name(),
					userMemberVo.getU_m_gender(), userMemberVo.getU_m_mail(), userMemberVo.getU_m_phone());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
