package com.mobileactionbootcamp.uincehw3.cus.service;

import com.mobileactionbootcamp.uincehw3.cus.converter.CusCustomerMapper;
import com.mobileactionbootcamp.uincehw3.cus.dao.CusCustomerDao;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerDto;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerSignUpDto;
import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import com.mobileactionbootcamp.uincehw3.veh.service.VehVehicleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CusCustomerService {

    private CusCustomerDao cusCustomerDao;
    private PasswordEncoder passwordEncoder;
    private VehVehicleService vehVehicleService;

    public CusCustomerDto customerSignUp(CusCustomerSignUpDto cusCustomerSignUpDto) {

        //CHECKS IF THERE ARE ANY USERNAME IDENTICAL TO THE DTO
        if(findInDB(cusCustomerSignUpDto.getUsername()) == null){
            CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.convertToCusCustomer(cusCustomerSignUpDto);

            //CHECKS UF THERE ARE NULL FIELDS
            if (!hasNullProperty(cusCustomer)){

                //ENCODES PASSWORD
                String encodedPassword = passwordEncoder.encode(cusCustomer.getPassword());
                cusCustomer.setPassword(encodedPassword);

                cusCustomer = cusCustomerDao.save(cusCustomer);
                CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);
                return cusCustomerDto;
            }
        }
        //RETURNS NULL IF cusCustomerSignUpDto CAN'T PASS CHECKS
        return null;
    }

    //RETURNS CUSTOMER IF GIVEN USERNAME EXISTS IN DB, OTHERWISE RETURNS NULL
    public CusCustomer findInDB(String username){
        List<CusCustomer> cusCustomerList = cusCustomerDao.findAll();

        for(CusCustomer customer : cusCustomerList){
            if(customer.getUsername().equals(username)){
                return customer;
            }
        }
        return null;
    }

    //FINDS AND RETURNS CUSTOMER BY ID FROM THE DB
    public CusCustomer findById(Long id){
        CusCustomer cusCustomer = cusCustomerDao.findById(id).orElseThrow();
        return cusCustomer;
    }

    private boolean hasNullProperty(CusCustomer cusCustomer){
        if(cusCustomer.getUsername() == null){
            return true;
        }
        if(cusCustomer.getFirstName() == null){
            return true;
        }
        if(cusCustomer.getLastName() == null){
            return true;
        }
        if(cusCustomer.getPassword() == null){
            return true;
        }
        return false;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        UserDetails userDetails = getLoggedUserDetails();
        CusCustomer cusCustomer = findInDB(userDetails.getUsername());

        if(cusCustomer != null){

            //CHECKS IF THE STORED ENCODED PASSWORD MATCHES WITH OLD PASSWORD
            if(passwordEncoder.matches(oldPassword, cusCustomer.getPassword())){
                String encodedPassword = passwordEncoder.encode(newPassword);
                cusCustomer.setPassword(encodedPassword);
                cusCustomerDao.save(cusCustomer);
                return true;
            }
        }
        return false;
    }

    //RETURNS LOGGED IN CUSTOMER'S USER DETAILS
    public UserDetails getLoggedUserDetails(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    //RETURNS LOGGED IN CUSTOMER
    public CusCustomer getLoggedCustomer(){
        UserDetails userDetails = getLoggedUserDetails();
        CusCustomer cusCustomer = findInDB(userDetails.getUsername());

        return cusCustomer;
    }

    //DELETES USER AND THE VEHICLES BELONG TO THE USER
    public void deleteUserAndVehicles() {
        CusCustomer cusCustomer = getLoggedCustomer();
        vehVehicleService.deleteVehiclesByUserId(cusCustomer.getId());
        cusCustomerDao.delete(cusCustomer);
    }
}
