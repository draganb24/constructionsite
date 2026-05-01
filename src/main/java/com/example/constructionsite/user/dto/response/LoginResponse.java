package com.example.constructionsite.user.dto.response;

import com.example.constructionsite.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
  private String token;
  private UserEntity userEntity;
}
