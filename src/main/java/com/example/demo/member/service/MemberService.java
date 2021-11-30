package com.example.demo.member.service;

import com.example.demo.authority.Authority;
import com.example.demo.member.dto.request.SignupRequest;
import com.example.demo.member.entity.Member;
import com.example.demo.member.exception.EmailDuplicationException;
import com.example.demo.member.exception.MemberNotFoundException;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@DependsOn({"securityConfig"})
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member signup(SignupRequest signupRequest) {
        Member member = Member.builder()
                .memberName(signupRequest.getMemberName())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .nickname(signupRequest.getNickname())
                .build();

        Authority authority = Authority.builder()
                .authorityName("ROLE_GUEST")
                .build();

        String memberName = member.getMemberName();

        if (memberRepository.existsByMemberName(memberName)) {
            throw new EmailDuplicationException(memberName);
        }

        member.addAuthority(authority);
        memberRepository.save(member);

        return member;
    }

    public Member login(String memberName, String password) {
        return memberRepository.findOneWithAuthoritiesByMemberName(memberName)
                .map(m -> {
                    m.login(passwordEncoder, password);
                    return m;
                })
                .orElseThrow(() -> new MemberNotFoundException(memberName));
    }

    public Member findOneByMemberName(String memberName) {
        return memberRepository.findOneWithAuthoritiesByMemberName(memberName)
                .orElseThrow(() -> new MemberNotFoundException(memberName));
    }

}
