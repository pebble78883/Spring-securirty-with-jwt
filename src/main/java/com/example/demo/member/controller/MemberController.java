package com.example.demo.member.controller;

import com.example.demo.common.dto.ApiResult;
import com.example.demo.common.error.exception.UnauthorizedException;
import com.example.demo.jwt.JwtAuthentication;
import com.example.demo.jwt.JwtAuthenticationToken;
import com.example.demo.member.dto.request.*;
import com.example.demo.member.dto.response.*;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api")
@RestController
public class MemberController {

    private final MemberService memberService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public MemberController(MemberService memberService, AuthenticationManager authenticationManager) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/member/signup")
    public ApiResult<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        Member member = memberService.signup(signupRequest);
        return ApiResult.ok(new SignupResponse(member.getMemberName()));
    }

    @PostMapping("/member/login")
    public ApiResult<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            JwtAuthenticationToken authenticationToken =
                    new JwtAuthenticationToken(loginRequest.getMemberName(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            String token = String.valueOf(authentication.getPrincipal());
            Member member = (Member) authentication.getDetails();

            return ApiResult.ok(new LoginResponse(token, member));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/member/me")
    public ApiResult<MeResponse> me(@AuthenticationPrincipal JwtAuthentication authentication) {
        Member member = memberService.findOneByMemberName(authentication.getMemberName());
        return ApiResult.ok(new MeResponse(member));
    }

}
