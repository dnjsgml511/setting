package com.test.szs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.szs.domain.UsersEntity;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    Optional<UsersEntity> findByUserIdAndPw(String userId,String pw);
    Optional<UsersEntity> findByUserId(String userId);
}
