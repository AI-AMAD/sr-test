package com.example.srtest.service.Impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.srtest.dto.MemberSignIn;
import com.example.srtest.dto.MemberSignUp;
import com.example.srtest.entity.Member;
import com.example.srtest.jwt.JwtUtil;
import com.example.srtest.repository.MemberRepository;
import com.example.srtest.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${jwt.secret}")
  private String secretKey;

  private Long expiredMs = 1000 * 60 * 60l;

  @Override
  @Transactional
  public Long signUp(MemberSignUp requestDto) throws Exception {
      if(memberRepository.findByEmail(requestDto.getEmail()).isPresent()){
          throw new Exception("이미 존재하는 이메일입니다.");
      }

      if(!requestDto.getPassword().equals(requestDto.getCheckedPassword())){
          throw new Exception("비밀번호가 일치하지 않습니다.");
      }


      Member member = memberRepository.save(requestDto.toEntity());
      member.encodePassword(passwordEncoder);


      member.addUserAuthority();
      return member.getId();
  }

  @Override
  public String generateToken(String username) {
      return JwtUtil.createJwt(username, secretKey, expiredMs);
  }


  public String signIn(MemberSignIn requestDto){

      Member member = memberRepository.findByEmail(requestDto.getEmail())
              .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일 입니다."));
      if(!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())){
          throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
      }

      return JwtUtil.createJwt(member.getNickname(), secretKey, expiredMs);
  } 
}
