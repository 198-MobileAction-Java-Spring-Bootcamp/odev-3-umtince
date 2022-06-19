package com.mobileactionbootcamp.uincehw3.jwt.controller;


import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerDto;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerSignUpDto;
import com.mobileactionbootcamp.uincehw3.jwt.dto.JwtLoginDto;
import com.mobileactionbootcamp.uincehw3.jwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity customerSignUp(@RequestBody CusCustomerSignUpDto cusCustomerSignUpDto){
        CusCustomerDto cusCustomerDto = authenticationService.customerSignUp(cusCustomerSignUpDto);
        if(cusCustomerDto != null){
            return ResponseEntity.ok(cusCustomerDto);
        }
        return ResponseEntity.badRequest().body("This username already exists!");
    }

    @PostMapping("/login")
    public ResponseEntity customerLogin(@RequestBody JwtLoginDto jwtLoginDto){
        String loginToken = authenticationService.login(jwtLoginDto);

        return ResponseEntity.ok(loginToken);
    }
}
