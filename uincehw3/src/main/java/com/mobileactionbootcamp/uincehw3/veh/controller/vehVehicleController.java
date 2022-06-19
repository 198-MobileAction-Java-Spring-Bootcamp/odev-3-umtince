package com.mobileactionbootcamp.uincehw3.veh.controller;

import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleDto;
import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleSaveRequestDto;
import com.mobileactionbootcamp.uincehw3.veh.service.VehVehicleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@AllArgsConstructor
public class vehVehicleController {

    private VehVehicleService vehVehicleService;

    //VEHICLE ADDING
    @PostMapping
    public ResponseEntity saveVehicle(@RequestBody VehVehicleSaveRequestDto vehVehicleSaveRequestDto){

        VehVehicleDto vehVehicleDto;
        try {
            vehVehicleDto = vehVehicleService.saveVehicle(vehVehicleSaveRequestDto);
        }
        catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }

        return ResponseEntity.ok(vehVehicleDto);
    }

    //RETURNS VEHICLES BY BRAND NAME
    @GetMapping("/brand/{brand}")
    public ResponseEntity getVehiclesByBrand(@PathVariable String brand){
        List<VehVehicleDto> vehVehicleDtoList = vehVehicleService.getVehiclesByBrand(brand);

        return ResponseEntity.ok(vehVehicleDtoList);
    }

    //RETURNS VEHICLES BY MODEL NAME
    @GetMapping("/model/{model}")
    public ResponseEntity getVehiclesByModel(@PathVariable String model){
        List<VehVehicleDto> vehVehicleDtoList = vehVehicleService.getVehiclesByModel(model);

        return ResponseEntity.ok(vehVehicleDtoList);
    }

    //RETURNS VEHICLES WHICH BELONG TO THE LOGGED IN USER
    @GetMapping("/user")
    public ResponseEntity getVehiclesOfUser(){
        List<VehVehicleDto> vehVehicleDtoList = vehVehicleService.getVehiclesOfUser();

        return ResponseEntity.ok(vehVehicleDtoList);
    }

    //UPDATES VEHICLE WHICH BELONGS TO THE LOGGED IN USER
    @PatchMapping("/update/{id}")
    public ResponseEntity updateVehicle(@PathVariable Long id ,@RequestBody VehVehicleSaveRequestDto vehVehicleSaveRequestDto){
        VehVehicleDto vehVehicleDto = null;
        try {
            vehVehicleDto = vehVehicleService.updateVehicle(id, vehVehicleSaveRequestDto);
            return ResponseEntity.ok(vehVehicleDto);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    //DELETES VEHICLES WHICH BELONGS TO THE LOGGED IN USER
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteVehicle(@PathVariable Long id){
        String returnMessage = "";
        try {
            returnMessage = vehVehicleService.deleteVehicleById(id);
            return ResponseEntity.ok(returnMessage);
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
