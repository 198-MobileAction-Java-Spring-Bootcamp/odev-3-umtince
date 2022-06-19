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

    //INJECTED USING LAZY CONSTRUCTOR TO PREVENT CIRCULAR DEPENDENCY
    private VehVehicleService(@Lazy CusCustomerService cusCustomerService){
        this.cusCustomerService = cusCustomerService;
    }

    public VehVehicleDto saveVehicle(VehVehicleSaveRequestDto vehVehicleSaveRequestDto) throws Exception{
        VehVehicle vehVehicle = null;

        //CHECKS IF LICENSE PLATE IS IN USE BY ANOTHER VEHICLE
        isVehicleExists(vehVehicleSaveRequestDto.getLicensePlate());

        //CHECKS THE INTEGRITY OF THE FIELDS
        if(checkInputIntegrity(vehVehicleSaveRequestDto)){
            vehVehicle = VehVehicleMapper.INSTANCE.convertToVehVehicle(vehVehicleSaveRequestDto);
            CusCustomer cusCustomer = cusCustomerService.getLoggedCustomer();
            vehVehicle.setCustomer(cusCustomer);
            vehVehicle = vehVehicleDao.save(vehVehicle);
        }
        return VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehVehicle);
    }

    //CHECKS AND THROWS EXCEPTION IF LICENSE PLATE ALREADY EXISTS IN THE DB
    private void isVehicleExists(String licensePlate) throws Exception{
        List<VehVehicle> vehVehicleList = getAllVehicles();

        for (VehVehicle vehVehicle : vehVehicleList){
            if (vehVehicle.getLicensePlate().equals(licensePlate)){
                throw new Exception("This vehicle already exists!");
            }
        }
    }

    //RETURNS ALL VEHICLES IN THE DB
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

    //RETURNS VEHICLES BY BRAND NAME
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

    //RETURNS VEHICLES BY MODEL NAME
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

    //RETURNS THE VEHICLES OF THE LOGGED IN USER
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

    //DELETES VEHICLES BELONG TO THE LOGGED IN USER VIA VEHICLE ID
    public void deleteVehiclesByUserId(Long userId){
        List<VehVehicle> vehVehicleList = getAllVehicles();

        for (VehVehicle vehicle: vehVehicleList){
            if(vehicle.getCustomer().getId().equals(userId)){
                vehVehicleDao.delete(vehicle);
            }
        }
    }

    //RETURNS VEHICLES BY ID
    private VehVehicle getVehicleById(Long id){
        return vehVehicleDao.findById(id).orElse(null);
    }

    //UPDATES LOGGED IN USER'S VEHICLE'S FIELDS
    public VehVehicleDto updateVehicle(Long id, VehVehicleSaveRequestDto vehVehicleSaveRequestDto) throws Exception {

        checkInputIntegrity(vehVehicleSaveRequestDto);
        //if not throws exception & does not continue to execute lines under

        //is there a vehicle with the given id
        VehVehicle vehicleToBeUpdated = getVehicleById(id);
        if(vehicleToBeUpdated == null){
            throw new Exception("No vehicle found with the given id!");
        }


        //who owns the license plate in the request body
        List<VehVehicle> vehVehicleList = getAllVehicles();
        String usernameOfLicensePlateOwner = null;
        for (VehVehicle vehicle : vehVehicleList){
            if(vehVehicleSaveRequestDto.getLicensePlate().equals(vehicle.getLicensePlate())){
                usernameOfLicensePlateOwner = vehicle.getCustomer().getUsername();
            }
        }

        //checks if the logged customer owns the yet to be updated license plate
        String usernameOfLoggedCustomer = cusCustomerService.getLoggedUserDetails().getUsername();
        if(usernameOfLicensePlateOwner != null && usernameOfLicensePlateOwner.equals(usernameOfLoggedCustomer) == false){
            throw new Exception("The license plate you like to update belongs to a vehicle that is not owned by you!");
        }

        //does the vehicle belong to the logged customer
        if(vehicleToBeUpdated.getCustomer().getUsername().equals(usernameOfLoggedCustomer)){
            vehicleToBeUpdated.setModelYear(vehVehicleSaveRequestDto.getModelYear());
            vehicleToBeUpdated.setModelName(vehVehicleSaveRequestDto.getModelName());
            vehicleToBeUpdated.setLicensePlate(vehVehicleSaveRequestDto.getLicensePlate());
            vehicleToBeUpdated.setBrandName(vehVehicleSaveRequestDto.getBrandName());
            vehicleToBeUpdated = vehVehicleDao.save(vehicleToBeUpdated);
            return VehVehicleMapper.INSTANCE.convertToVehVehicleDto(vehicleToBeUpdated);
        }
        else {
            throw new Exception("You don't own a vehicle with the given id!");
        }
    }

    //DELETES THE LOGGED IN USER'S VEHICLE VIA VEHICLE ID
    public String deleteVehicleById(Long id) throws Exception{
        VehVehicle vehVehicle = getVehicleById(id);
        //checks if there are any vehicle with given id
        if(vehVehicle == null){
            throw new Exception("No vehicle found with the given id!");
        }

        //checks is logged in user has a vehicle with the given id
        String usernameOfLoggedCustomer = cusCustomerService.getLoggedUserDetails().getUsername();
        if(vehVehicle.getCustomer().getUsername().equals(usernameOfLoggedCustomer) == false){
            throw new Exception("You don't own a vehicle with the given id!");
        }

        vehVehicleDao.delete(vehVehicle);

        return "Deleted successfully!";
    }
}
