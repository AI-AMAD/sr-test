package com.example.srtest.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.srtest.entity.RefreshToken;
import com.example.srtest.repository.MemberRepository;
import com.example.srtest.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final MemberRepository memberRepository;

  public RefreshToken createRefreshToken(String username){
    RefreshToken refreshToken = RefreshToken.builder()
            .member(memberRepository.findByEmail(username).get())
            .token(UUID.randomUUID().toString())
            .expiryDate(Instant.now().plusMillis(600000))
            .build();

    return refreshTokenRepository.save(refreshToken);
  }

  public Optional<RefreshToken> findByToken(String token){
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken verifyExpiration(RefreshToken token){
    if(token.getExpiryDate().compareTo(Instant.now())<0){
        refreshTokenRepository.delete(token);
        throw new RuntimeException(token.getToken() + " Refresh Token was expired. Please make a new signin request");

    }
    return token;
  } 
}
