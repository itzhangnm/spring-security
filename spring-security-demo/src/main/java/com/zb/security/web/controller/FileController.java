package com.zb.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * {@link File} {@link Controller}
 *
 * @author zb
 * @date 2018/12/20 16:53
 */
@RestController
@RequestMapping("/file")
public class FileController {
	
	private final String tempLocal = "E:\\java_code\\spring-security\\spring-security-demo\\src\\main\\java\\com\\zb\\web\\controller\\";
	
	@PostMapping("/upload")
	public String upload(MultipartFile file) throws IOException {
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getContentType());
		File outFile = new File(tempLocal + file.getOriginalFilename());
		FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(outFile));
		return outFile.getAbsolutePath();
	}
	
	@GetMapping("/download")
	public void download(HttpServletResponse response) {
		File file = new File(tempLocal + "name.txt");
		try (
				InputStream inputStream = new FileInputStream(file);
				OutputStream outputStream = response.getOutputStream()
		) {
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename=test.txt");
			FileCopyUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
