package com.shield.Shield.controller;

import com.shield.Shield.entity.Driver;
import com.shield.Shield.repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shield/driver")
public class DriverController {

    @Autowired
    private DriverRepo driverRepo ;

    @PostMapping("/create-driver")
    public ResponseEntity<?> createDriver(@RequestBody Driver driver){
        Driver driver1 = driverRepo.save(driver) ;
        return ResponseEntity.ok(driver1);
    }

    @PostMapping("/driver-login")
    public String driverLogin(@RequestBody Driver driver)
    {
        Driver driverEmail = driverRepo.findByEmail(driver.getEmail());
        if (driverEmail.getPassword().matches(driver.getPassword()))
            return "Login Successfully" ;
        else
            return "invalid credentail" ;
    }

}
