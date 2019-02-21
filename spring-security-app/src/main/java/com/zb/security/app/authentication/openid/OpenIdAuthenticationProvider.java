package com.zb.security.app.authentication.openid;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * openId认证 provider
 *
 * @author zb
 * @date 2019/2/15 17:29
 */
@RequiredArgsConstructor
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

	private final SocialUserDetailsService userDetailsService;

	private final UsersConnectionRepository usersConnectionRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.isInstanceOf(OpenIdAuthenticationToken.class, authentication, "unsupported authentication type");
		Assert.isTrue(!authentication.isAuthenticated(), "already authenticated");
		OpenIdAuthenticationToken authToken = (OpenIdAuthenticationToken) authentication;
		Set<String> providerUserIds = new HashSet<>();
		providerUserIds.add((String) authToken.getPrincipal());
		String userId = toUserId(authToken.getProviderId(), providerUserIds);
		
		if (userId == null) {
			throw new BadCredentialsException("Unknown access token");
		}
		UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
		if (userDetails == null) {
			throw new UsernameNotFoundException("Unknown connected account openId");
		}
		OpenIdAuthenticationToken openIdAuthenticationToken = new OpenIdAuthenticationToken(userDetails, userDetails.getAuthorities());
		openIdAuthenticationToken.setDetails(authToken.getDetails());
		return openIdAuthenticationToken;
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private String toUserId(String providerId, Set<String> openIds) {
		Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(providerId,openIds);
		return (userIds.size() == 1) ? userIds.iterator().next() : null;
	}
}
