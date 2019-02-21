package com.zb.security.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zb.security.core.properties.LoginResponseType;
import com.zb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * 认证成功处理器
 *
 * @author zb
 * @date 2019/1/4 14:02
 */
@Component
@Slf4j
public class AppAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	
	private final ObjectMapper objectMapper;

	private final SecurityProperties securityProperties;
	
	private final ClientDetailsService clientDetailsService;
	
	private final AuthorizationServerTokenServices authorizationServerTokenServices;
	
	

	public AppAuthenticationSuccessHandler(ObjectMapper objectMapper, SecurityProperties securityProperties, ClientDetailsService clientDetailsService, AuthorizationServerTokenServices authorizationServerTokenServices) {
		this.objectMapper = objectMapper;
		this.securityProperties = securityProperties;
		this.clientDetailsService = clientDetailsService;
		this.authorizationServerTokenServices = authorizationServerTokenServices;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.toLowerCase().startsWith("basic ")) {
			return;
		}
		String[] tokens = extractAndDecodeHeader(header, request);
		assert tokens.length == 2;
		String clientId = tokens[0];
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"custom");
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(objectMapper.writeValueAsString(accessToken));
	}
		
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
		throws IOException {
		
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}
		
		String token = new String(decoded, "UTF-8");
		
		int delimit = token.indexOf(":");
		
		if (delimit == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[]{token.substring(0, delimit), token.substring(delimit + 1)};
	}
}
