package com.example.constructionsite.user.controller;

import com.example.constructionsite.user.dto.request.LoginRequest;
import com.example.constructionsite.user.dto.response.LoginResponse;
import com.example.constructionsite.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/authenticate")
public class UserController {

  private final UserService userService;

  @PostMapping(value = "/login", consumes = "application/json")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(userService.loginUser(loginRequest));
  }

}
