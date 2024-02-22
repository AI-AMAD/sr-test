package com.example.srtest.service;

import com.example.srtest.dto.MemberSignIn;
import com.example.srtest.dto.MemberSignUp;

public interface MemberService {
    public String signIn(MemberSignIn requestDto) throws Exception;
    public Long signUp(MemberSignUp requestDto) throws Exception;
    public String generateToken(String username);
}
