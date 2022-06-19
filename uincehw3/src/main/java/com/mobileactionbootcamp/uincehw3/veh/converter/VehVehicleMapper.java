package com.mobileactionbootcamp.uincehw3.veh.converter;

import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleDto;
import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleSaveRequestDto;
import com.mobileactionbootcamp.uincehw3.veh.entity.VehVehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehVehicleMapper {
    VehVehicleMapper INSTANCE = Mappers.getMapper(VehVehicleMapper.class);

    VehVehicle convertToVehVehicle(VehVehicleSaveRequestDto vehVehicleSaveRequestDto);
    VehVehicleDto convertToVehVehicleDto(VehVehicle vehVehicle);
}
