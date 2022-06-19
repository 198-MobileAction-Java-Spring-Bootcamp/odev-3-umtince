package com.mobileactionbootcamp.uincehw3.veh.converter;

import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleDto;
import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleSaveRequestDto;
import com.mobileactionbootcamp.uincehw3.veh.entity.VehVehicle;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-19T22:16:19+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.1 (Oracle Corporation)"
)
public class VehVehicleMapperImpl implements VehVehicleMapper {

    @Override
    public VehVehicle convertToVehVehicle(VehVehicleSaveRequestDto vehVehicleSaveRequestDto) {
        if ( vehVehicleSaveRequestDto == null ) {
            return null;
        }

        VehVehicle vehVehicle = new VehVehicle();

        vehVehicle.setBrandName( vehVehicleSaveRequestDto.getBrandName() );
        vehVehicle.setModelYear( vehVehicleSaveRequestDto.getModelYear() );
        vehVehicle.setModelName( vehVehicleSaveRequestDto.getModelName() );
        vehVehicle.setLicensePlate( vehVehicleSaveRequestDto.getLicensePlate() );

        return vehVehicle;
    }

    @Override
    public VehVehicleDto convertToVehVehicleDto(VehVehicle vehVehicle) {
        if ( vehVehicle == null ) {
            return null;
        }

        VehVehicleDto vehVehicleDto = new VehVehicleDto();

        vehVehicleDto.setId( vehVehicle.getId() );
        vehVehicleDto.setBrandName( vehVehicle.getBrandName() );
        vehVehicleDto.setModelYear( vehVehicle.getModelYear() );
        vehVehicleDto.setModelName( vehVehicle.getModelName() );
        vehVehicleDto.setLicensePlate( vehVehicle.getLicensePlate() );
        vehVehicleDto.setCustomer( vehVehicle.getCustomer() );

        return vehVehicleDto;
    }
}
