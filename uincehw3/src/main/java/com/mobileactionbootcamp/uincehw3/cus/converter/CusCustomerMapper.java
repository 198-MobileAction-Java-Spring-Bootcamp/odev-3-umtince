package com.mobileactionbootcamp.uincehw3.cus.converter;

import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerDto;
import com.mobileactionbootcamp.uincehw3.cus.dto.CusCustomerSignUpDto;
import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CusCustomerMapper {
    CusCustomerMapper INSTANCE = Mappers.getMapper(CusCustomerMapper.class);

    CusCustomer convertToCusCustomer(CusCustomerSignUpDto cusCustomerSignUpDto);
    CusCustomerDto convertToCusCustomerDto(CusCustomer cusCustomer);
}
