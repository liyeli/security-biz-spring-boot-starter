package org.springframework.security.boot.biz.authentication.nested;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.boot.biz.SpringSecurityBizMessageSource;
import org.springframework.security.boot.biz.exception.AuthResponse;
import org.springframework.security.boot.biz.exception.AuthResponseCode;
import org.springframework.security.boot.biz.exception.AuthenticationCaptchaExpiredException;
import org.springframework.security.boot.biz.exception.AuthenticationCaptchaIncorrectException;
import org.springframework.security.boot.biz.exception.AuthenticationCaptchaInvalidException;
import org.springframework.security.boot.biz.exception.AuthenticationCaptchaNotFoundException;
import org.springframework.security.boot.biz.exception.AuthenticationCaptchaSendException;
import org.springframework.security.boot.biz.exception.AuthenticationMethodNotSupportedException;
import org.springframework.security.boot.biz.exception.AuthenticationOverRetryRemindException;
import org.springframework.security.boot.biz.exception.AuthenticationTokenExpiredException;
import org.springframework.security.boot.biz.exception.AuthenticationTokenIncorrectException;
import org.springframework.security.boot.biz.exception.AuthenticationTokenInvalidException;
import org.springframework.security.boot.biz.exception.AuthenticationTokenNotFoundException;
import org.springframework.security.boot.utils.SubjectUtils;
import org.springframework.security.core.AuthenticationException;

import com.alibaba.fastjson.JSONObject;

/**
 * Post认证请求失败后的处理实现
 */
public class DefaultMatchedAuthenticationFailureHandler  implements MatchedAuthenticationFailureHandler {

	protected MessageSourceAccessor messages = SpringSecurityBizMessageSource.getAccessor();
	 
	@Override
	public boolean supports(AuthenticationException e) {
		return SubjectUtils.isAssignableFrom(e.getClass(), AuthenticationMethodNotSupportedException.class,
				AuthenticationCaptchaNotFoundException.class, AuthenticationCaptchaIncorrectException.class,
				AuthenticationTokenNotFoundException.class, AuthenticationTokenIncorrectException.class,
				AuthenticationTokenExpiredException.class);
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
 
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		if (e instanceof AuthenticationMethodNotSupportedException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_METHOD_NOT_ALLOWED.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_METHOD_NOT_ALLOWED.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationOverRetryRemindException) {
				JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_OVER_RETRY_REMIND.getCode(),
						messages.getMessage(AuthResponseCode.SC_AUTHC_OVER_RETRY_REMIND.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationCaptchaSendException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_CAPTCHA_SEND_FAIL.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_CAPTCHA_SEND_FAIL.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationCaptchaNotFoundException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_CAPTCHA_REQUIRED.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_CAPTCHA_REQUIRED.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationCaptchaExpiredException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_CAPTCHA_EXPIRED.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_CAPTCHA_EXPIRED.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationCaptchaIncorrectException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_CAPTCHA_INCORRECT.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_CAPTCHA_INCORRECT.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationCaptchaInvalidException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_CAPTCHA_INVALID.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_CAPTCHA_INVALID.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationTokenNotFoundException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHZ_TOKEN_REQUIRED.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHZ_TOKEN_REQUIRED.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationTokenExpiredException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHZ_TOKEN_EXPIRED.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHZ_TOKEN_EXPIRED.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationTokenIncorrectException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHZ_TOKEN_INCORRECT.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHZ_TOKEN_INCORRECT.getMsgKey(), e.getMessage())));
		} else if (e instanceof AuthenticationTokenInvalidException) {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHZ_TOKEN_INVALID.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHZ_TOKEN_INVALID.getMsgKey(), e.getMessage())));
		} else {
			JSONObject.writeJSONString(response.getWriter(), AuthResponse.of(AuthResponseCode.SC_AUTHC_FAIL.getCode(),
					messages.getMessage(AuthResponseCode.SC_AUTHC_FAIL.getMsgKey())));
		}
		
	}

}
