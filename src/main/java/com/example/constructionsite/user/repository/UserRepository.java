package com.example.constructionsite.user.repository;

import com.example.constructionsite.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByFullName(String fullName);

  Optional<UserEntity> findByEmail(String email);
}
