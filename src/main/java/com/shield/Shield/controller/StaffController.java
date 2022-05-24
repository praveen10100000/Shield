package com.shield.Shield.controller;

import com.shield.Shield.ResponseInJson;
import com.shield.Shield.entity.Company;
import com.shield.Shield.entity.Staff;
import com.shield.Shield.service.CompanyService;
import com.shield.Shield.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shield")
public class StaffController {

    @Autowired
    private CompanyService companyService ;

    @Autowired
    private StaffService staffService ;

    @PostMapping("/create-staff")
    public ResponseEntity<?> createStaff (@RequestBody Staff staff){
        Company company  = companyService.getCompanyDataFromToken();
        ResponseInJson responseInJson = new ResponseInJson() ;
        responseInJson.setCode(200);
        responseInJson.setHttpCode(200);
        responseInJson.setMessage("Success");
        Staff staff1 = staffService.addDetails(staff) ;
        responseInJson.setData(staff1);
        return ResponseEntity.ok(responseInJson);
    }

    @PostMapping("/staff-login")
    public ResponseEntity<?> stafflogin (@RequestBody Staff staff)
    {

        return ResponseEntity.ok("");
    }


}
