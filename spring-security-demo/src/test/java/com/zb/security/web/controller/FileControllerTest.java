package com.zb.security.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;

/**
 * {@link File} 上传下载测试类
 *
 * @author zb
 * @date 2018/12/20 16:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {
	
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	@Test
	public void whenUploadSuccess() throws Exception {
		String filePath = mockMvc.perform(MockMvcRequestBuilders.multipart("/file/upload")
				.file(new MockMultipartFile("file", "name.txt", "multipart/post-date", "hello world!".getBytes("UTF-8"))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString();
		System.out.println(filePath);
	}
	
	@Test
	public void whenDownloadSuccess() throws Exception {
		String local = "E:\\java_code\\spring-security\\spring-security-demo\\src\\test\\java\\com\\zb\\web\\controller\\";
		byte[] array = mockMvc.perform(MockMvcRequestBuilders.get("/file/download"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsByteArray();
		InputStream inputStream = new ByteArrayInputStream(array);
		OutputStream outputStream = new FileOutputStream(new File(local + "text.txt"));
		FileCopyUtils.copy(inputStream,outputStream);
	}
}
