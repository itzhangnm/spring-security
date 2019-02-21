package com.zb.security.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户实体
 *
 * @author zb
 * @date 2018/12/17 11:33
 */
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class User {
	
	public interface UserSimpleView{};
	
	public interface UserInfoView extends UserSimpleView{};
	
	
	private Long id;
	
	@JsonView(UserSimpleView.class)
	private String userName;
	
	@JsonView(UserInfoView.class)
	private String passWord;
	
	private Integer age;
	
	private Date birthDay;
}
