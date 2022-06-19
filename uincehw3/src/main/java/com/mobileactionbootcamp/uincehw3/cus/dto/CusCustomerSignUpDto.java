package com.mobileactionbootcamp.uincehw3.cus.dto;

import lombok.Data;
@Data
public class CusCustomerSignUpDto {
    //NO ID FIELD BECAUSE THIS DTO WILL BE USED TO GET SIGN UP DETAILS
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
