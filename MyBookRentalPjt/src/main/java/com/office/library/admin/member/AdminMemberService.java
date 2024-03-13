package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service //스프링 컨테이너에 빈 객체로 생성되기 위해 명시
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_CREATE_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	@Autowired
	AdminMemberDao adminMemberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	//회원가입 처리 메서드
	public int createAccountConfirm(AdminMemberVo adminMemberVo) {
		
		boolean isMember =  adminMemberDao.isAdminMember(adminMemberVo.getA_m_id()); // 중복 아이디 체크 isMember가 true이면 이미 사용중인 아이디이고 false이면 사용 가능한 아이디!
		
		if(!isMember) { //여기선 !isMember가 true이면  AdminMemberDao의 insertAdminAccount 메서드를 실행한다.
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);
			
			if (result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS; // 1: 성공
			else 
				return ADMIN_ACCOUNT_CREATE_FAIL; // -1: 실패 (DB INSERT 실패) 
		} else {
			return ADMIN_ACCOUNT_CREATE_EXIST; // 0: 실패 (중복 아이디)
		}
	}
	
	//로그인 확인(관리자 로그인 인증)
	public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {// AdminMemberController에서 보낸 매개변수를 받는다
		
		AdminMemberVo loginAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo); //AdminMemberDao의 리턴값인 adminMemberVos.get(0) or null을 담은 것
		
		if(loginAdminMemberVo != null) 
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");
		
		else 
			
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		
		return loginAdminMemberVo;
	}
	
	//관리자 목록
	public List<AdminMemberVo> listupAdmin() { 
		return adminMemberDao.selectAdmins();
	}
	
	//관리자 승인
	public void setAdminApproval(int a_m_no) {
		
		int result = adminMemberDao.updateAdminAccount(a_m_no);
	}
	
	//회원 정보 업데이트
	public int modifyAccountConfirm(AdminMemberVo adminMemberVo) {
		return adminMemberDao.updateAdminAccount(adminMemberVo);
	}
	
	//가장 최근 관리자 정보 가져오기
	public AdminMemberVo getLoginedAdminMemberVo(int a_m_no) {
		return adminMemberDao.selectAdmin(a_m_no);
	}
	
	public int findPasswordConfirm(AdminMemberVo adminMemberVo) {
		
		AdminMemberVo selectedAdminMemberVo =
				adminMemberDao.selectAdmin(adminMemberVo.getA_m_id(),
						adminMemberVo.getA_m_name(), adminMemberVo.getA_m_mail());
		
		int result = 0;
		
		if(selectedAdminMemberVo == null) {
			String newPassword = createNewPassword(); //난수를 이용해 새로운 비밀번호를 생성
			result = adminMemberDao.updatePassword(adminMemberVo.getA_m_id(), newPassword);
		
		if(result > 0)
			sendNewPasswordByMail(adminMemberVo.getA_m_mail(), newPassword);
	}
		return result;
		
	}
	
	private String createNewPassword() {
		
		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				'u', 'v', 'w', 'x', 'y', 'z'
				};

		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
		
			if (index % 2 == 0) 
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			else
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
		
		}

                System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());
		
		return stringBuffer.toString();
		
	}
	
	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		
		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo("mkhong916@gmail.com"); //받는 메일 주소
				mimeMessageHelper.setTo(toMailAddr); 
				mimeMessageHelper.setSubject("[한국 도서관] 새 비밀번호 안내입니다."); //제목
				mimeMessageHelper.setText("새 비밀번호 : " + newPassword, true); // 내용 
 				
			}
			
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
		
	}
}
