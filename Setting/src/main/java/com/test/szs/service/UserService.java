package com.test.szs.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.test.szs.config.jwt.TokenUtils;
import com.test.szs.domain.AuthEntity;
import com.test.szs.domain.TokenResponse;
import com.test.szs.domain.UsersEntity;
import com.test.szs.repository.AuthRepository;
import com.test.szs.repository.UsersRepository;
import com.test.szs.request.UserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UsersRepository usersRepository;
	private final TokenUtils tokenUtils;
	private final AuthRepository authRepository;

	public Optional<UsersEntity> findByUserId(String userId) {

		return usersRepository.findByUserId(userId);
	}

	@Transactional
	public TokenResponse signUp(UserRequest userRequest) {
		UsersEntity usersEntity = usersRepository
				.save(UsersEntity.builder().pw(userRequest.getUserPw()).userId(userRequest.getUserId()).build());

		String accessToken = tokenUtils.generateJwtToken(usersEntity);
		String refreshToken = tokenUtils.saveRefreshToken(usersEntity);

		authRepository.save(AuthEntity.builder().usersEntity(usersEntity).refreshToken(refreshToken).build());

		return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
	}

	@Transactional
	public TokenResponse signIn(UserRequest userRequest) {
		UsersEntity usersEntity = usersRepository.findByUserIdAndPw(userRequest.getUserId(), userRequest.getUserPw())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
		AuthEntity authEntity = authRepository.findByUsersEntityId(usersEntity.getId())
				.orElseThrow(() -> new IllegalArgumentException("Token 이 존재하지 않습니다."));
		String accessToken = "";
		String refreshToken = authEntity.getRefreshToken();

		if (tokenUtils.isValidRefreshToken(refreshToken)) {
			accessToken = tokenUtils.generateJwtToken(authEntity.getUsersEntity());
			return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(authEntity.getRefreshToken())
					.build();
		} else {
			refreshToken = tokenUtils.saveRefreshToken(usersEntity);
			authEntity.refreshUpdate(refreshToken);
		}

		return TokenResponse.builder().ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken).build();
	}

	public List<UsersEntity> findUsers() {
		return usersRepository.findAll();
	}
}