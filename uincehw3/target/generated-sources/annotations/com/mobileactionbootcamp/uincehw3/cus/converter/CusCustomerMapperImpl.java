package com.mobileactionbootcamp.uincehw3.cus.converter;

import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerDto;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerSignUpDto;
import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-19T22:16:19+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
public class CusCustomerMapperImpl implements CusCustomerMapper {

    @Override
    public CusCustomer convertToCusCustomer(CusCustomerSignUpDto cusCustomerSignUpDto) {
        if ( cusCustomerSignUpDto == null ) {
            return null;
        }

        CusCustomer cusCustomer = new CusCustomer();

        cusCustomer.setFirstName( cusCustomerSignUpDto.getFirstName() );
        cusCustomer.setLastName( cusCustomerSignUpDto.getLastName() );
        cusCustomer.setUsername( cusCustomerSignUpDto.getUsername() );
        cusCustomer.setPassword( cusCustomerSignUpDto.getPassword() );

        return cusCustomer;
    }

    @Override
    public CusCustomerDto convertToCusCustomerDto(CusCustomer cusCustomer) {
        if ( cusCustomer == null ) {
            return null;
        }

        CusCustomerDto cusCustomerDto = new CusCustomerDto();

        cusCustomerDto.setId( cusCustomer.getId() );
        cusCustomerDto.setFirstName( cusCustomer.getFirstName() );
        cusCustomerDto.setLastName( cusCustomer.getLastName() );
        cusCustomerDto.setUsername( cusCustomer.getUsername() );
        cusCustomerDto.setPassword( cusCustomer.getPassword() );

        return cusCustomerDto;
    }
}
