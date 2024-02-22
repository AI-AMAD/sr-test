package com.example.srtest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.srtest.dto.JwtResponse;
import com.example.srtest.dto.MemberSignIn;
import com.example.srtest.dto.MemberSignUp;
import com.example.srtest.service.MemberService;
import com.example.srtest.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@RequestBody MemberSignIn request) throws Exception {

      String accessToken = memberService.signIn(request);
      String refreshToken = refreshTokenService.createRefreshToken(request.getEmail()).getToken();
      JwtResponse jwtResponse = JwtResponse.builder()
              .accessToken(accessToken)
              .refreshToken(refreshToken)
              .build();

      return ResponseEntity.ok().body(jwtResponse);
  }

  @PostMapping("/join")
  @ResponseStatus(HttpStatus.OK)
  public Long join(@Valid @RequestBody MemberSignUp request) throws Exception {
      return memberService.signUp(request);
  }
}
