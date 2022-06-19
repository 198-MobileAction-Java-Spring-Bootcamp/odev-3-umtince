package com.mobileactionbootcamp.uincehw3.veh.service;

import com.mobileactionbootcamp.uincehw3.cus.entity.CusCustomer;
import com.mobileactionbootcamp.uincehw3.cus.service.CusCustomerService;
import com.mobileactionbootcamp.uincehw3.veh.converter.VehVehicleMapper;
import com.mobileactionbootcamp.uincehw3.veh.dao.VehVehicleDao;
import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleDto;
import com.mobileactionbootcamp.uincehw3.veh.dto.VehVehicleSaveRequestDto;
import com.mobileactionbootcamp.uincehw3.veh.entity.VehVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehVehicleService {

    @Autowired
    private VehVehicleDao vehVehicleDao;
    private CusCustomerService cusCustomerService;

    private VehVehicleService(@Lazy CusCustomerService cusCustomerService){
        this.cusCustomerService = cusCustomerService;
    }

    public VehVehicleDto saveVehicle(VehVehicleSaveRequestDto vehVehicleSaveRequestDto) throws Exception{
        VehVehicle vehVehicle = null;

        isVehicleExists(vehVehicleSaveRequestDto.getLicensePlate());

        if(checkInputIntegrity(vehVehicleSaveRequestDto)){
            vehVehicle = VehVehicleMapper.INSTANCE.convertToVehVehicle(vehVehicleSaveRequestDto);
            CusCustomer cusCustomer = cusCustomerService.getLoggedCustomer();
            vehVehicle.setCustomer(cusCustomer);
            vehVehicle = vehVehicleDao.save(vehVehicle);
        }
        return VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehVehicle);
    }

    private void isVehicleExists(String licensePlate) throws Exception{
        List<VehVehicle> vehVehicleList = getAllVehicles();

        for (VehVehicle vehVehicle : vehVehicleList){
            if (vehVehicle.getLicensePlate().equals(licensePlate)){
                throw new Exception("This vehicle already exists!");
            }
        }
    }

    private List<VehVehicle> getAllVehicles(){
        return vehVehicleDao.findAll();
    }
    private boolean checkInputIntegrity(VehVehicleSaveRequestDto vehVehicleSaveRequestDto) throws Exception{

        if(checkModelYear(vehVehicleSaveRequestDto.getModelYear()) == false){
            throw new Exception("Model year is not correct!");
        }
        if(checkBrandNameAndModelName(vehVehicleSaveRequestDto.getBrandName()) == false){
            throw new Exception("Brand name or Model name is not correct!");
        }
        if(checkLicensePlate(vehVehicleSaveRequestDto.getLicensePlate()) == false){
            throw new Exception("License plate should only consist of numbers and capital letters!");
        }

        return true;
    }

    private boolean checkModelYear(String modelYear){
        if(modelYear.length() != 4){
            return false;
        }

        for(int i=0; i<modelYear.length(); i++){
            if (modelYear.charAt(i) < '0' || modelYear.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    private boolean checkBrandNameAndModelName(String name){
        if(name.equals("") || name == null){
            return false;
        }
        return true;
    }

    private boolean checkLicensePlate(String licensePlate){
        for(int i=0; i<licensePlate.length(); i++){
            if(Character.isDigit(licensePlate.charAt(i))){
                continue;
            }
            else if (licensePlate.charAt(i) < 'A' || licensePlate.charAt(i) > 'Z'){
                return false;
            }
        }
        return true;
    }

    public List<VehVehicleDto> getVehiclesByBrand(String brandName) {
        List<VehVehicleDto> vehVehicleDtoList = new ArrayList<>();
        List<VehVehicle> vehVehicleList = getAllVehicles();

        for(VehVehicle vehicle : vehVehicleList){
            if(vehicle.getBrandName().equals(brandName)){
                vehVehicleDtoList.add(VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehicle));
            }
        }
        return vehVehicleDtoList;
    }

    public List<VehVehicleDto> getVehiclesByModel(String modelName) {
        List<VehVehicleDto> vehVehicleDtoList = new ArrayList<>();
        List<VehVehicle> vehVehicleList = getAllVehicles();

        for(VehVehicle vehicle : vehVehicleList){
            if(vehicle.getModelName().equals(modelName)){
                vehVehicleDtoList.add(VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehicle));
            }
        }
        return vehVehicleDtoList;
    }

    public List<VehVehicleDto> getVehiclesOfUser() {
        List<VehVehicleDto> userVehicleList = new ArrayList<>();
        List<VehVehicle> vehVehicleList = getAllVehicles();

        String loggedUsername = cusCustomerService.getLoggedUserDetails().getUsername();

        for(VehVehicle vehicle : vehVehicleList){
            if(vehicle.getCustomer().getUsername().equals(loggedUsername)){
                userVehicleList.add(VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehicle));
            }
        }
        return userVehicleList;
    }

    public void deleteVehiclesByUserId(Long userId){
        List<VehVehicle> vehVehicleList = getAllVehicles();

        for (VehVehicle vehicle: vehVehicleList){
            if(vehicle.getCustomer().getId().equals(userId)){
                vehVehicleDao.delete(vehicle);
            }
        }
    }

    private VehVehicle getVehicleById(Long id){
        return vehVehicleDao.findById(id).orElse(null);
    }

    public VehVehicleDto updateVehicle(Long id, VehVehicleSaveRequestDto vehVehicleSaveRequestDto) throws Exception {

        checkInputIntegrity(vehVehicleSaveRequestDto);
        //if not throws exception & does not continue to execute lines under

        List<VehVehicle> vehVehicleList = getAllVehicles();
        String usernameOfLoggedCustomer = cusCustomerService.getLoggedUserDetails().getUsername();

        VehVehicle vehicleToBeUpdated = getVehicleById(id);

        if(vehicleToBeUpdated == null){
            throw new Exception("No vehicle found with the given id!");
        }

        //does the vehicle belong to the logged customer
        if(vehicleToBeUpdated.getId().equals(id) && vehicleToBeUpdated.getCustomer().getUsername().equals(usernameOfLoggedCustomer)){
            for (VehVehicle vehicle : vehVehicleList){

                //proceeds if the license plate belongs to a vehicle owned by the logged customer
                if(vehVehicleSaveRequestDto.getLicensePlate().equals(vehicle.getLicensePlate()) && vehicle.getCustomer().getUsername().equals(usernameOfLoggedCustomer)){
                    vehicle.setModelYear(vehVehicleSaveRequestDto.getModelYear());
                    vehicle.setModelName(vehVehicleSaveRequestDto.getModelName());
                    vehicle.setLicensePlate(vehVehicleSaveRequestDto.getLicensePlate());
                    vehicle.setBrandName(vehVehicleSaveRequestDto.getBrandName());
                    vehicle = vehVehicleDao.save(vehicle);
                    return VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehicle);
                }
                else{
                    throw new Exception("The license plate you like to update belongs to a vehicle that is not owned by you!");
                }
            }
        }
        else {
            throw new Exception("You don't own a vehicle with the given id!");
        }


        return null;
    }
}
