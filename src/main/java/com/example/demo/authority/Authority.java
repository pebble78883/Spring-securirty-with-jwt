package com.example.demo.authority;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Authority {

    @Id
    @Column(name = "authority_name", length = 30)
    private String authorityName;

}