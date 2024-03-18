package com.office.library.user.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	
	public UserMemberVo selectUser (UserMemberVo userMemberVo) {  // UserMemberService에서 보낸 매개변수를 받는다
		System.out.println("[UserMemberDao] selectUser()");
		
		String sql = "SELECT * FROM tbl_user_member "
				   + "WHERE u_m_id = ?"; //좌측과 같은 조건에 sql을 조회하라
		
		List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();  // 리스트 타입의 객체 생성
		
		try {                                 // sql문, sql 결과값의 타입, ?에 들어갈 변수	
			userMemberVos = jdbcTemplate.query(sql, new RowMapper<UserMemberVo>() { //userMemberVos에 저 쿼리의 결과를 담는다!
				
				@Override
				public UserMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException { //mapRow()는 ResultSet과 행의 개수를 파라미터로 받는다
					
					// rs에 있는 데이터를 adminMemberVo객체의 setter를 이용해서 저장
					UserMemberVo userMemberVo = new UserMemberVo();
					
					userMemberVo.setU_m_no(rs.getInt("U_m_no"));
					userMemberVo.setU_m_id(rs.getString("U_m_id"));
					userMemberVo.setU_m_pw(rs.getString("U_m_pw"));
					userMemberVo.setU_m_name(rs.getString("U_m_name"));
					userMemberVo.setU_m_gender(rs.getString("U_m_gender"));
					userMemberVo.setU_m_mail(rs.getString("U_m_mail"));
					userMemberVo.setU_m_phone(rs.getString("U_m_phone"));
					userMemberVo.setU_m_reg_date(rs.getString("U_m_reg_date"));
					userMemberVo.setU_m_mod_date(rs.getString("U_m_mod_date"));
											
					return userMemberVo;
				}
				
			}, userMemberVo.getU_m_id());
			
			if(!passwordEncoder.matches(userMemberVo.getU_m_pw(),
					userMemberVos.get(0).getU_m_pw()))
					userMemberVos.clear();
		} catch (Exception e)  {
			e.printStackTrace();
		}
		
		return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
	}
	
	public int updateUserAccount(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberDao] updateUserAccount()");
		
		String sql =  "UPDATE tbl_user_member SET "
					+ "u_m_name = ?, "
					+ "u_m_gender = ?, "
					+ "u_m_mail = ?, "
					+ "u_m_phone = ?, "
					+ "u_m_mod_date = NOW() "
					+ "WHERE u_m_no = ?";
		
		int result = -1;
		
		try {
			
			result = jdbcTemplate.update(sql, 
										 userMemberVo.getU_m_name(), 
										 userMemberVo.getU_m_gender(), 
										 userMemberVo.getU_m_mail(), 
										 userMemberVo.getU_m_phone(), 
										 userMemberVo.getU_m_no());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
				
		return result;
	}
	
	public UserMemberVo selectUser(int u_m_no) {
		System.out.println("[UserMemberDao] selectUser()");
		
		String sql =  "SELECT * FROM tbl_user_member "
					+ "WHERE u_m_no = ?";
		
		List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();
		
		try {
			
			userMemberVos = jdbcTemplate.query(sql, new RowMapper<UserMemberVo>() {
				
				@Override
				public UserMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					UserMemberVo userMemberVo = new UserMemberVo();
					
					userMemberVo.setU_m_no(rs.getInt("u_m_no"));
					userMemberVo.setU_m_id(rs.getString("u_m_id"));
					userMemberVo.setU_m_pw(rs.getString("u_m_pw"));
					userMemberVo.setU_m_name(rs.getString("u_m_name"));
					userMemberVo.setU_m_gender(rs.getString("u_m_gender"));
					userMemberVo.setU_m_mail(rs.getString("u_m_mail"));
					userMemberVo.setU_m_phone(rs.getString("u_m_phone"));
					userMemberVo.setU_m_reg_date(rs.getString("u_m_reg_date"));
					userMemberVo.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return userMemberVo;
					
				}
				
			}, u_m_no);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
		
	}
	
	public UserMemberVo selectUser(String u_m_id, String u_m_name, String u_m_mail) {
		System.out.println("[UserMemberDao] selectUser()");
		
		String sql =  "SELECT * FROM tbl_user_member "
					+ "WHERE u_m_id = ? AND u_m_name = ? AND u_m_mail = ?";
	
		List<UserMemberVo> userMemberVos = new ArrayList<UserMemberVo>();
		
		try {
			
			userMemberVos = jdbcTemplate.query(sql, new RowMapper<UserMemberVo>() {
				
				@Override
				public UserMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					UserMemberVo userMemberVo = new UserMemberVo();
					
					userMemberVo.setU_m_no(rs.getInt("u_m_no"));
					userMemberVo.setU_m_id(rs.getString("u_m_id"));
					userMemberVo.setU_m_pw(rs.getString("u_m_pw"));
					userMemberVo.setU_m_name(rs.getString("u_m_name"));
					userMemberVo.setU_m_gender(rs.getString("u_m_gender"));
					userMemberVo.setU_m_mail(rs.getString("u_m_mail"));
					userMemberVo.setU_m_phone(rs.getString("u_m_phone"));
					userMemberVo.setU_m_reg_date(rs.getString("u_m_reg_date"));
					userMemberVo.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return userMemberVo;
					
				}
				
			}, u_m_id, u_m_name, u_m_mail);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return userMemberVos.size() > 0 ? userMemberVos.get(0) : null;
		
	}
	
	public int updatePassword(String u_m_id, String newPassword) {
		System.out.println("[UserMemberDao] updatePassword()");
		
		String sql =  "UPDATE tbl_user_member SET "
					+ "u_m_pw = ?,  u_m_mod_date = NOW() "
					+ "WHERE u_m_id = ?";
		
		int result = -1;
		
		try {
			
			result = jdbcTemplate.update(sql, passwordEncoder.encode(newPassword), u_m_id);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return result;
	}

}
