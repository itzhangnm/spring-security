package com.zb.security.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.model.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author zb
 * @date 2018/12/17 11:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
	
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	
	@Test
	public void whenListSuccess() throws Exception {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("userName", "zb");
		map.add("age", "0");
//		map.add("page","10");
//		map.add("size","0");
//		map.add("sort","userName,desc");
		String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/list").params(map).contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void whenGetInfoSuccess() throws Exception {
		String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("zb"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}
	
	@Test
	public void whenGetInfoFail() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	public void whenCreateSuccess() throws Exception {
		User user = new User();
		user.setUserName("zb");
		user.setAge(18);
		String userJson = mapper.writer().writeValueAsString(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(user.getUserName()));
	}
	
	@Test
	public void whenCreateFail() throws Exception {
		User user = new User()
				.setUserName("zb")
				.setAge(-1);
		String userJson = mapper.writer().writeValueAsString(user);
		mockMvc.perform(MockMvcRequestBuilders.post("/user/add").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	public void whenUpdateSuccess() throws Exception {
		User user = new User()
				.setUserName("zb")
				.setAge(10)
				.setBirthDay(new Date());
		String userJson = mapper.writer().writeValueAsString(user);
		System.out.println(userJson);
		mockMvc.perform(MockMvcRequestBuilders.put("/user/update/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void whenUpdateFail() throws Exception{
		Date date = Date.from(LocalDate.now().plusYears(1L).atStartOfDay(ZoneId.systemDefault()).toInstant());
		User user = new User()
				.setId(1L)
				.setUserName("zb")
				.setAge(1)
				.setBirthDay(date);
		String userJson = mapper.writer().writeValueAsString(user);
		System.out.println(userJson);
		mockMvc.perform(MockMvcRequestBuilders.put("/user/update/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	public void whenDeleteSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/101").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void whenCheckSuccess() throws Exception {
		int status = mockMvc.perform(MockMvcRequestBuilders.get("/user/check/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.param("userName", "zb"))
				.andReturn().getResponse().getStatus();
		System.out.println(status);
	}
	
	
}
