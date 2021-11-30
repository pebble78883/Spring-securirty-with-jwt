package com.example.demo.member.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class LoginRequest {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Size(max = 30)
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "이메일 형식에 맞지 않습니다.")
    private String memberName;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]{8,50}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자를 사용하세요.")
    private String password;

}
