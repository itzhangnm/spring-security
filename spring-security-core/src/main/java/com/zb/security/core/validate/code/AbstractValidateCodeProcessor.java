package com.zb.security.core.validate.code;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 抽象校验码处理器
 *
 * @author zb
 * @date 2019/1/15 15:48
 */
@Getter
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor{

	
	protected final Map<String,ValidateCodeGenerator> validateCodeGenerators;
	
	protected final ValidateCodeType validateCodeType;
	
	protected final ValidateCodeRepository validateCodeRepository;
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	protected AbstractValidateCodeProcessor(Map<String, ValidateCodeGenerator> validateCodeGenerators, ValidateCodeType validateCodeType, ValidateCodeRepository validateCodeRepository) {
		this.validateCodeGenerators = validateCodeGenerators;
		this.validateCodeType = validateCodeType;
		this.validateCodeRepository = validateCodeRepository;
	}
	
	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generator(request);
		save(request,validateCode);
		send(request,validateCode);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeType type = getValidateCodeType();
		C code = (C) validateCodeRepository.get(request, validateCodeType);
		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), type.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("验证码获取失败");
		}
		if (StringUtils.isBlank(codeInRequest)){
			throw new ValidateCodeException("验证码值不能为空");
		}
		if (code == null){
			throw new ValidateCodeException("验证码不存在");
		}
		if (code.isExpire()){
			validateCodeRepository.remove(request, validateCodeType);
			throw new ValidateCodeException("验证码已过期");
		}
		if (!StringUtils.equals(codeInRequest,code.getCode())){
			throw new ValidateCodeException("验证码错误");
		}
		validateCodeRepository.remove(request, validateCodeType);
	}
	
	/**
	 * 发送验证码
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;
	
	
	
	
	/**
	 * 保存校验码到session
	 * @param request ServletWebRequest
	 * @param validateCode C
	 */
	private void save(ServletWebRequest request,C validateCode){
		validateCodeRepository.save(request,validateCode,validateCodeType);
	}
	
	/**
	 * 生成校验码实体
	 * @param request
	 * @return
	 */
	private C generator(ServletWebRequest request){
		ValidateCodeType validateType = getValidateCodeType();
		ValidateCodeGenerator generator = validateCodeGenerators.values().stream()
				.filter(g -> g.getValidateCodeType().equals(validateType))
				.findFirst().orElseThrow(() -> new ValidateCodeException("验证码生成器类型"+validateType.toString()+"不存在"));
		return generator.generate(request);
	}
	
	
}
