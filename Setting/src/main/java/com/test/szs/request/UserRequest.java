package com.test.szs.request;

import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
public class UserRequest {
	@ApiParam(value = "유저 아이디", required = true, example = "admin")
	private String userId;
	@ApiParam(value = "유저 비밀번호", required = false, example = "123123")
	private String userPw;
}
