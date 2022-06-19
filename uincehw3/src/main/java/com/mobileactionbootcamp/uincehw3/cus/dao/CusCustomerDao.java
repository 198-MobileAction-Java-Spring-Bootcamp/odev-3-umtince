package com.mobileactionbootcamp.uincehw3.cus.dao;

import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CusCustomerDao extends JpaRepository<CusCustomer, Long> {
}
