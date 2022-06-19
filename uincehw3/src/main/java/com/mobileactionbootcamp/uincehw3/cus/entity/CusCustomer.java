package com.mobileactionbootcamp.uincehw3.cus.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class CusCustomer {
    @Id
    @SequenceGenerator(name = "CusCustomer", sequenceName = "CUS_CUSTOMER_ID_SEQ")
    @GeneratedValue(generator = "CusCustomer")
    private Long id;

    @Column(name = "FIRST_NAME" ,nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME" ,nullable = false)
    private String lastName;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 25)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

}
