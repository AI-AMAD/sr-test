package com.example.srtest.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.example.srtest.entity.Member;
import com.example.srtest.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUp {
  @NotBlank(message = "아이디를 입력해주세요.")
  private String email;

  @NotBlank(message = "닉네임을 입력해주세요.")
  @Size(min=2, message = "닉네임이 너무 짧습니다.")
  private String nickname;

  @NotNull(message = "나이를 입력해주세요")
  @Range(min = 0, max = 150)
  private int age;

  @NotBlank(message = "비밀번호를 입력해주세요.")
  private String password;

  private String checkedPassword;

  private com.example.srtest.entity.Role role;

  @Builder
  public Member toEntity() {
      return Member.builder()
              .email(email)
              .nickname(nickname)
              .age(age)
              .password(password)
              .role(Role.USER)
              .build();
  }
}
