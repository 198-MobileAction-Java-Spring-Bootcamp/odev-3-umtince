package com.mobileactionbootcamp.uincehw3.veh.dao;

import com.mobileactionbootcamp.uincehw3.veh.entity.VehVehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehVehicleDao extends JpaRepository<VehVehicle, Long> {
}
