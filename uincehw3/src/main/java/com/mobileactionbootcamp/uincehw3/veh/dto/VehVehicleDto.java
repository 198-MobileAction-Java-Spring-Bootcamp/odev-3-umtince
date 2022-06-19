package com.mobileactionbootcamp.uincehw3.veh.dto;

import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import lombok.Data;

@Data
public class VehVehicleDto {
    private Long id;
    private String brandName;
    private String modelYear;
    private String modelName;
    private String licensePlate;
    private CusCustomer customer;
}
