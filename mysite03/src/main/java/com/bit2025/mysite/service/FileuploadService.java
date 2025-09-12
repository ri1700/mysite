package com.bit2025.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileuploadService {
	private static final String SAVE_PATH = "/mysite-uploads";
	private static final String URL = "/upload-images";
	
	public String restore(MultipartFile multipartFile) throws RuntimeException {
		try {
			
			File uploadDirectory = new File(SAVE_PATH);
			
			if(!uploadDirectory.exists() && !uploadDirectory.mkdirs()) {
				return null;
			}
			
			if(multipartFile.isEmpty()) {
				return null;
			}
			
			String originalFilename = multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFilename(extName);
			long fileSize = multipartFile.getSize();
			
			System.out.println("originalFilename:" + originalFilename);
			System.out.println("fileSize:" + fileSize);
			
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
		
			return URL + "/" + saveFilename;
			
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private String generateSaveFilename(String extName) {
		Calendar calendar = Calendar.getInstance();
		return  "" +
				calendar.get(Calendar.YEAR)   + 
				calendar.get(Calendar.MONTH)  + 
				calendar.get(Calendar.DATE)   + 
				calendar.get(Calendar.HOUR)   +
				calendar.get(Calendar.MINUTE) +
				calendar.get(Calendar.SECOND) +
				calendar.get(Calendar.MILLISECOND) +
				("." + extName);
	}

}