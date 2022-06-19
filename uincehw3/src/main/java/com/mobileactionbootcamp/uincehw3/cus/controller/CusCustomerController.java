package com.mobileactionbootcamp.uincehw3.cus.controller;

import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerDto;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerSignUpDto;
import com.mobileactionbootcamp.uincehw3.cus.service.CusCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CusCustomerController {

    private CusCustomerService cusCustomerService;

    @PatchMapping("/password")
    public ResponseEntity changeCustomerPassword(@RequestParam String oldPassword, String newPassword){
        boolean isChangeSuccessful = cusCustomerService.changePassword(oldPassword, newPassword);

        if(isChangeSuccessful){
            return ResponseEntity.ok("Password changed successfully!");
        }
        return ResponseEntity.ok("An ERROR occurred while changing password");
    }

    @DeleteMapping
    public ResponseEntity deleteUser(){
        cusCustomerService.deleteUser();
        return ResponseEntity.ok(null);
    }
}
