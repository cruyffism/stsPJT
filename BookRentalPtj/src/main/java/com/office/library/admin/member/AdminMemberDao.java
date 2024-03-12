package com.office.library.admin.member;

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
	 	
	 	public AdminMemberVo selectAdmin(AdminMemberVo adminMemberVo) { // AdminMemberService에서 보낸 매개변수를 받는다
	 		System.out.println("[AdminMemberDao] selectAdmin()");
	 		
	 		String sql = "SELECT * FROM tbl_admin_member "
	 				   + "WHERE a_m_id = ? AND a_m_approval > 0"; //좌측과 같은 조건에 sql을 조회하라
	 		
	 		
	 		List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>(); // 리스트 타입의 객체 생성
	 		
	 		try {										// sql문, sql 결과값의 타입, ?에 들어갈 변수			
	 				adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {  //adminMemberVos에 저 쿼리의 결과를 담는다!
	 					
	 					@Override
	 					public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException { //mapRow()는 ResultSet과 행의 개수를 파라미터로 받는다
	 						
	 						// rs에 있는 데이터를 adminMemberVo객체의 setter를 이용해서 저장
	 						AdminMemberVo adminMemberVo = new AdminMemberVo();
	 						
	 						adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
	 						adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
	 						adminMemberVo.setA_m_id(rs.getString("a_m_id"));
	 						adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
	 						adminMemberVo.setA_m_name(rs.getString("a_m_name"));
	 						adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
	 						adminMemberVo.setA_m_part(rs.getString("a_m_part"));
	 						adminMemberVo.setA_m_position(rs.getString("a_m_position"));
	 						adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
	 						adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
	 						adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
	 						adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
	 						
	 						return adminMemberVo; // 저장이 끝나면 AdminMemberVo의 객체를 반환! >> 이 반환된 객체는 List 타입으로 저장된 후 adminMemberVos에 할당된다.
	 						
	 						}
	 						
	 					}, adminMemberVo.getA_m_id()); // 여기 getA_m_id()가 위에 sql문 ?에 들어감
	 						//암호화된 문자열을 비교하는 메서드 :passwordEncoder의 matches()
	 					if (!passwordEncoder.matches(adminMemberVo.getA_m_pw(), // matches(a,b) 결과가 false라면 비밀번호가 안 맞는걸로 판단하여 if문 true타고
	 						 adminMemberVos.get(0).getA_m_pw()))
	 						 adminMemberVos.clear();    //조회된 데이터는 삭제
	 							
	 				} catch (Exception e) { //시스템적인 에러 
	 					e.printStackTrace();
	 			}
	 			
	 			return 	adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null; // 0보다 크면 로그인 인증에 성공 및 조회된 관리자 정보를 서비스에 반환 
	 		}                                                                     // 길이가 0 이하라면 로그인 인증 실패 및 서비스에 null 반환
	 		
	 	public List<AdminMemberVo> selectAdmins() {
			System.out.println("[AdminMemberDao] selectAdmins()");
			
			String sql =  "SELECT * FROM tbl_admin_member";
		
			List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
			
			try {
				
				adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
					
					@Override
					public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						AdminMemberVo adminMemberVo = new AdminMemberVo();
						
						adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
						adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
						adminMemberVo.setA_m_id(rs.getString("a_m_id"));
						adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
						adminMemberVo.setA_m_name(rs.getString("a_m_name"));
						adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
						adminMemberVo.setA_m_part(rs.getString("a_m_part"));
						adminMemberVo.setA_m_position(rs.getString("a_m_position"));
						adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
						adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
						adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
						adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
						
						return adminMemberVo;
						
					}
					
				});
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
			return adminMemberVos;
			
		}
	 		public int updateAdminAccount(int a_m_no) {
	 			System.out.println("[AdminMemberDao] updateAdminAccount()");
	 			
	 			String sql = "UPDATE tbl_admin_member SET "
	 					   + "a_m_approval = 1 "
	 					   + "WHERE a_m_no = ?";
	 			
	 			int result = -1;
	 			
	 			try {
	 				
	 				result = jdbcTemplate.update(sql, a_m_no);
	 				
	 			} catch (Exception e) {
					e.printStackTrace();
	 		}
	 			return result;
	 	}
	 		
	}
	
	 	

	

