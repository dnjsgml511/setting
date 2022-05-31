package com.test.szs.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.szs.domain.TokenResponse;
import com.test.szs.domain.UsersEntity;
import com.test.szs.request.UserRequest;
import com.test.szs.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@Operation(summary = "test hello", description = "hello api example")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "OK !!"),
			@ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
			@ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!") })
	@PostMapping("/user/signUp")
	public ResponseEntity<?> signUp(@RequestBody UserRequest userRequest) {
		return userService.findByUserId(userRequest.getUserId()).isPresent() ? ResponseEntity.badRequest().build()
				: ResponseEntity.ok(userService.signUp(userRequest));
	}

	@PostMapping("/user/signIn")
	public ResponseEntity<TokenResponse> signIn(@RequestBody UserRequest userRequest) {

		return ResponseEntity.ok().body(userService.signIn(userRequest));
	}

	@GetMapping("/info")
	public ResponseEntity<List<UsersEntity>> findUser() {
		return ResponseEntity.ok().body(userService.findUsers());
	}
}