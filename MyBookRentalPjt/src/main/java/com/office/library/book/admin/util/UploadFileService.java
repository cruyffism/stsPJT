package com.office.library.book.admin.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	public String upload(MultipartFile file) { //MultipartFile 타입의 파일을 업로드 
		
		boolean result = false; 
		
		//파일저장
		String fileOriName = file.getOriginalFilename(); //관리자가 업로드한 원본 파일의 이름을 가져옴
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length()); //관리자가 업로드한 원본 파일의 확장자를 가져옴
		String uploadDir = "C:\\library\\upload\\"; //서버에서 파일이 저장되는 위치
		
		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replace("-", ""); // 하이픈 제거
		
		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		
		if(!saveFile.exists())
			saveFile.mkdirs();
		
		try {
			file.transferTo(saveFile);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(result) {
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!");
			return uniqueName + fileExtension;
			
		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!");
			
			return null;
		}
		
	}

}
