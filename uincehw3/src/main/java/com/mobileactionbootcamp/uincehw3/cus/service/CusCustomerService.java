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
        if(findInDB(cusCustomerSignUpDto.getUsername()) == null){
            CusCustomer cusCustomer = CusCustomerMapper.INSTANCE.convertToCusCustomer(cusCustomerSignUpDto);
            if (!hasNullProperty(cusCustomer)){

                String encodedPassword = passwordEncoder.encode(cusCustomer.getPassword());
                cusCustomer.setPassword(encodedPassword);

                cusCustomer = cusCustomerDao.save(cusCustomer);
                CusCustomerDto cusCustomerDto = CusCustomerMapper.INSTANCE.convertToCusCustomerDto(cusCustomer);
                return cusCustomerDto;
            }
        }
        return null;
    }

    public CusCustomer findInDB(String username){
        List<CusCustomer> cusCustomerList = cusCustomerDao.findAll();

        for(CusCustomer customer : cusCustomerList){
            if(customer.getUsername().equals(username)){
                return customer;
            }
        }
        return null;
    }

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
            if(passwordEncoder.matches(oldPassword, cusCustomer.getPassword())){
                String encodedPassword = passwordEncoder.encode(newPassword);
                cusCustomer.setPassword(encodedPassword);
                cusCustomerDao.save(cusCustomer);
                return true;
            }
        }
        return false;
    }

    public UserDetails getLoggedUserDetails(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public CusCustomer getLoggedCustomer(){
        UserDetails userDetails = getLoggedUserDetails();
        CusCustomer cusCustomer = findInDB(userDetails.getUsername());

        return cusCustomer;
    }

    public void deleteUser() {
        CusCustomer cusCustomer = getLoggedCustomer();
        vehVehicleService.deleteVehiclesByUserId(cusCustomer.getId());
        cusCustomerDao.delete(cusCustomer);
    }
}
