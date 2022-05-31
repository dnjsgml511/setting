package com.test.szs.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Table(name = "auth")
@Entity
public class AuthEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private String refreshToken;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UsersEntity usersEntity;

	@Builder
	public AuthEntity(String refreshToken, UsersEntity usersEntity) {
		this.refreshToken = refreshToken;
		this.usersEntity = usersEntity;
	}

	public void refreshUpdate(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}