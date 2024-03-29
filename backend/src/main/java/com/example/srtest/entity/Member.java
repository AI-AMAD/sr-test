package com.example.srtest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Member {
  @Id @Column(name = "member_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 45, unique = true)
  private String email;

  @Column(length = 45)
  private String nickname;

  private int age;

  @Column(length = 100)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  public void addUserAuthority() {
      this.role = Role.USER;
  }

  public void encodePassword(PasswordEncoder passwordEncoder){
      this.password = passwordEncoder.encode(password);
  }
}
