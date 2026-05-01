package com.example.constructionsite.user.service;

import com.example.constructionsite.security.JwtService;
import com.example.constructionsite.user.dto.request.LoginRequest;
import com.example.constructionsite.user.dto.response.LoginResponse;
import com.example.constructionsite.user.entity.UserEntity;
import com.example.constructionsite.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserEntity> user = userRepository.findByFullName(username);

    if (user.isPresent()) {
      var userObject = user.get();
      return org.springframework.security.core.userdetails.User.builder()
          .username(userObject.getFullName())
          .password(userObject.getPassword())
          .build();
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  public LoginResponse loginUser(LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        )
    );
    var user = userRepository.findByEmail(loginRequest.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronađen"));

    var jwtToken = jwtService.generateToken(user);

    return LoginResponse.builder()
        .token(jwtToken)
        .userEntity(user)
        .build();
  }
}
