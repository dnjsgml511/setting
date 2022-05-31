package com.test.szs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.szs.domain.AuthEntity;

public interface AuthRepository extends JpaRepository<AuthEntity, Long> {
	Optional<AuthEntity> findByUsersEntityId(Long userId);
}