package br.com.caelum.carangobom.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.caelum.carangobom.config.security.TokenService;
import br.com.caelum.carangobom.dto.TokenDto;
import br.com.caelum.carangobom.form.LoginForm;

class AuthenticationControllerTest {
	
	private AuthenticationController authenticationController;
	
	@Mock
	private TokenService tokenService;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@BeforeEach
	public void config() {
		openMocks(this);
		
		authenticationController = new AuthenticationController(authenticationManager, tokenService);
	}

	@Test
	void shouldReturnOk() {
		LoginForm form = new LoginForm("admin", "123456");
		String testToken = "TEST_TOKEN";
		
		setTheSecurityAuthentication(form);
		when(authenticationManager.authenticate(form.convert())).thenReturn(getTheSecurityAuthentication());
		when(tokenService.generateToken(getTheSecurityAuthentication())).thenReturn(testToken);
		
		ResponseEntity<TokenDto> response = authenticationController.authenticate(form);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(testToken, response.getBody().getToken());
	}
	
	@Test
	void shouldReturnBadRequest() {
		LoginForm form = new LoginForm("teste", "123456");
		
		when(authenticationManager.authenticate(any())).thenThrow(new UsernameNotFoundException("error"));
		
		ResponseEntity<TokenDto> response = authenticationController.authenticate(form);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	private void setTheSecurityAuthentication(LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = form.convert();
		SecurityContextHolder.getContext().setAuthentication(loginData);
	}
	
	private Authentication getTheSecurityAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
