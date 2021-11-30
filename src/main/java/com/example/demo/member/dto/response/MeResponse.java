package com.example.demo.member.dto.response;

import com.example.demo.authority.Authority;
import com.example.demo.member.entity.Member;
import lombok.Getter;

@Getter
public class MeResponse {

    private final String memberName;

    private final String nickname;

    private final String profileImage;

    private final String[] roles;

    public MeResponse(final Member member) {
        this.memberName = member.getMemberName();
        this.nickname = member.getNickname();
        this.profileImage = member.getProfileImage().orElse(null);
        this.roles = member.getAuthorities().stream()
                .map(Authority::getAuthorityName)
                .toArray(String[]::new);
    }

}
