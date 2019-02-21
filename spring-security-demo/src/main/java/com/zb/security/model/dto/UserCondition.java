package com.zb.security.model.dto;


import com.zb.security.model.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * {@link User} 查询实体
 *
 * @author zb
 * @date 2018/12/17 11:33
 */
@Getter
@Setter
public class UserCondition {
	
	@NotBlank(message = "名称不能为空")
	private String userName;
	
	@NotNull(message = "年龄不能为空")
	@Range(message = "年龄不能为负")
	private Integer age;
	
	@Past(message = "生日必须是一个过去的时间")
	private Date birthDay;
}
