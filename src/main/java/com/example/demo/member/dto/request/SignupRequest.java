package com.example.demo.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignupRequest {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "아이디는 영문 대소문자 혹은 숫자를 사용하세요.")
    private String memberName;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]{8,50}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용하세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 4, max = 20, message = "닉네임은 4~20자 사이 한글 혹은 영문대소문자 및 숫자를 사용하세요.")
    private String nickname;

}
