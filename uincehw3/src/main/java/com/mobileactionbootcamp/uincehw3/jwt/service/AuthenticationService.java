package com.mobileactionbootcamp.uincehw3.jwt.service;

import com.mobileactionbootcamp.uincehw3.cus.converter.CusCustomerMapper;
import com.mobileactionbootcamp.uincehw3.cus.dao.CusCustomerDao;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerDto;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerSignUpDto;
import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import com.mobileactionbootcamp.uincehw3.cus.service.CusCustomerService;
import com.mobileactionbootcamp.uincehw3.jwt.dto.JwtLoginDto;
import com.mobileactionbootcamp.uincehw3.jwt.security.JwtTokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private CusCustomerService cusCustomerService;
    private JwtTokenGenerator jwtTokenGenerator;

    public CusCustomerDto customerSignUp(CusCustomerSignUpDto cusCustomerSignUpDto) {
        return cusCustomerService.customerSignUp(cusCustomerSignUpDto);
    }


    public String login(JwtLoginDto jwtLoginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(jwtLoginDto.getUsername(), jwtLoginDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenGenerator.generatreJwtToken(authentication);

        String fullToken = "Bearer " + token;

        return fullToken;
    }
}
