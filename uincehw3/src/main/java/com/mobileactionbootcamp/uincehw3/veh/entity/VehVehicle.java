package com.mobileactionbootcamp.uincehw3.veh.entity;

import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "VEHICLE")
public class VehVehicle {

    @Id
    @SequenceGenerator(name = "VehVehicle", sequenceName = "VEH_VEHICLE_ID_SEQ")
    @GeneratedValue(generator = "VehVehicle")
    private Long id;

    @Column(name = "BRAND_NAME", nullable = false, length = 20)
    private String brandName;
    @Column(name = "MODEL_YEAR", nullable = false, length = 4)
    private String modelYear;
    @Column(name = "MODEL_NAME", nullable = false, length = 20)
    private String modelName;
    @Column(name = "LICENSE_PLATE", nullable = false, unique = true ,length = 10)
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private CusCustomer customer;
}
