package com.mobileactionbootcamp.uincehw3.jwt.security;

import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import com.mobileactionbootcamp.uincehw3.cus.service.CusCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final CusCustomerService cusCustomerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CusCustomer cusCustomer = cusCustomerService.findInDB(username);

        return JwtUserDetails.create(cusCustomer);
    }

    public UserDetails loadUserById(Long id){
        CusCustomer cusCustomer = cusCustomerService.findById(id);
        return JwtUserDetails.create(cusCustomer);
    }
}
