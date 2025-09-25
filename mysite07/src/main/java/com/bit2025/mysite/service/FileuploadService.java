package com.bit2025.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:config/fileupload.properties")
public class FileuploadService {
	private Environment env;
	
	public FileuploadService(Environment env) {
		this.env = env;
	}
	
	public String restore(MultipartFile multipartFile) throws RuntimeException {
		try {
			
			File uploadDirectory = new File(env.getProperty("fileupload.uploadLocation"));
			
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
			OutputStream os = new FileOutputStream(env.getProperty("fileupload.uploadLocation") + "/" + saveFilename);
			os.write(data);
			os.close();
		
			return env.getProperty("fileupload.resourceUrl") + "/" + saveFilename;
			
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
