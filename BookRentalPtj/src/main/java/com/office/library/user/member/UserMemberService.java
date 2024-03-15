package com.office.library.user.member;

import java.security.SecureRandom;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {

	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	@Autowired
	UserMemberDao userMemberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;

	// 회원 가입 처리 메서드
	public int createAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] createAccountConfirm()");

		boolean isMember = userMemberDao.isUserMember(userMemberVo.getU_m_id()); // 기존에 회원 정보 있는지 체크 >> false이면 기존에 회원
																					// 정보 없는거

		if (!isMember) { // 기존에 회원정보가 없으면 if문은 true이므로
			int result = userMemberDao.insertUserAccount(userMemberVo); // 회원 정보 저장, UserMemberDao의 리턴값인 result를 담은 것.

			if (result > 0)
				return USER_ACCOUNT_CREATE_SUCCESS; // 1 저장이 잘 됐을때
			else
				return USER_ACCOUNT_CREATE_FAIL; // -1 저장 실패
		} else {
			return USER_ACCOUNT_ALREADY_EXIST; // 0 회원 정보 있을때
		}

	}

	// 로그인 확인
	public UserMemberVo loginConfirm(UserMemberVo userMemberVo) { // UserMemberController에서 보낸 매개변수를 받는다
		System.out.println("[UserMemberService] loginConfirm()");

		UserMemberVo loginedUserMemberVo = userMemberDao.selectUser(userMemberVo); // UserMemberDao의 리턴값인
																					// adminMemberVos.get(0) or null을 담은
																					// 것

		if (loginedUserMemberVo != null)
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");

		else
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");

		return loginedUserMemberVo;

	}
	
	public int modifyAccountConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] modifyAccountConfirm()");
		
		return userMemberDao.updateUserAccount(userMemberVo);
		
	}
	
	public UserMemberVo getLoginedUserMemberVo(int u_m_no) {
		System.out.println("[UserMemberService] getLoginedUserMemberVo()");
		
		return userMemberDao.selectUser(u_m_no);
		
	}
	
	public int findPasswordConfirm(UserMemberVo userMemberVo) {
		System.out.println("[UserMemberService] findPasswordConfirm()");
		
		UserMemberVo selectedUserMemberVo = userMemberDao.selectUser(userMemberVo.getU_m_id(),
																	 userMemberVo.getU_m_name(), 
																	 userMemberVo.getU_m_mail());
		
		int result = 0;
		
		if (selectedUserMemberVo != null) {
			
			String newPassword = createNewPassword();
			result = userMemberDao.updatePassword(userMemberVo.getU_m_id(), newPassword);

			if (result > 0)
				sendNewPasswordByMail(userMemberVo.getU_m_mail(), newPassword);
			
		} 
		
		return result;
		
	}
	
	private String createNewPassword() {
		System.out.println("[AdminMemberService] createNewPassword()");
		
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
		System.out.println("[AdminMemberService] sendNewPasswordByMail()");
		
		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo("mkhong916@gmail.com");	// 수신 가능한 개인 메일 주소
				mimeMessageHelper.setTo(toMailAddr);
				mimeMessageHelper.setSubject("[한국 도서관] 새 비밀번호 안내입니다.");
				mimeMessageHelper.setText("새 비밀번호 : " + newPassword, true);
				
			}
			
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
		
	}


}
