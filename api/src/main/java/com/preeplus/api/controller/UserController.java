package com.preeplus.api.controller;


import com.preeplus.api.vo.LoginParams;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@RestController
public class UserController {

	private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(UserController.class);
	private final AuthenticationManager authenticationManager;

	public UserController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@RequestMapping(value = "/api/login")
	public ResponseEntity<Object> login(@RequestBody LoginParams params, HttpSession session) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(params.getUsername(), params.getPassword());

		try {
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.ok(false);
		}
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/api/test", method = RequestMethod.GET)
	public ResponseEntity<Principal> test(Principal principal) {
		return ResponseEntity.ok(principal);
	}

	@Secured("ROLE_USER")
	@RequestMapping(value = "/api/roles", method = RequestMethod.GET)
	public ResponseEntity roles(HttpServletRequest request, HttpSession session) {

		return ResponseEntity.ok(request.getUserPrincipal());
	}

	// 로그아웃
	@RequestMapping(value = "/api/logout", method = RequestMethod.POST)
	public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if ( authentication != null ) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}

	// 로그인 체크
	@RequestMapping(value = "/api/check_login", method = RequestMethod.POST)
	public ResponseEntity check_login(HttpServletRequest request, HttpServletResponse response, Principal principal) throws IOException, ServletException {
		boolean isLogin = request.isUserInRole("ROLE_USER");

		if (isLogin) {
			return ResponseEntity.ok(principal.getName());
		}
		return ResponseEntity.ok(false);
	}
}
