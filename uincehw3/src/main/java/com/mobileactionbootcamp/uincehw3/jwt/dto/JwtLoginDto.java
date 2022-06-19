package com.mobileactionbootcamp.uincehw3.jwt.dto;

import lombok.Data;

@Data
public class JwtLoginDto {
    private String username;
    private String password;
}
