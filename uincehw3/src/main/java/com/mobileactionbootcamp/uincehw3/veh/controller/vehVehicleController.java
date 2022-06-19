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

    @GetMapping("/brand/{brand}")
    public ResponseEntity getVehiclesByBrand(@PathVariable String brand){
        List<VehVehicleDto> vehVehicleDtoList = vehVehicleService.getVehiclesByBrand(brand);

        return ResponseEntity.ok(vehVehicleDtoList);
    }

    @GetMapping("/model/{model}")
    public ResponseEntity getVehiclesByModel(@PathVariable String model){
        List<VehVehicleDto> vehVehicleDtoList = vehVehicleService.getVehiclesByModel(model);

        return ResponseEntity.ok(vehVehicleDtoList);
    }

    @GetMapping("/user")
    public ResponseEntity getVehiclesOfUser(){
        List<VehVehicleDto> vehVehicleDtoList = vehVehicleService.getVehiclesOfUser();

        return ResponseEntity.ok(vehVehicleDtoList);
    }

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
}
