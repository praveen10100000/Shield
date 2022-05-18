package com.shield.Shield.controller;

import com.shield.Shield.ResponseInJson;
import com.shield.Shield.entity.Company;
import com.shield.Shield.repository.CompanyRepo;
import com.shield.Shield.service.CompanyService;
import com.shield.Shield.serviceImpl.CompanyServiceImp;
import com.shield.Shield.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shield")
public class CompanyController {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyServiceImp companyService2;

    @PostMapping("/registration-second")
    public ResponseEntity<?> companyRegistration(@RequestBody Company company) {
        ResponseInJson<Company> responseInJson = new ResponseInJson();
        responseInJson.setCode(200);
        responseInJson.setHttpCode(200);
        responseInJson.setMessage("Success");
        Company company1 = companyService.addDetails(company);
        responseInJson.setData(company1);
        return ResponseEntity.ok(responseInJson);
    }

    @PostMapping("/login")
    public ResponseEntity<?> companyLogin(@RequestBody Company company) throws Exception {
        if (companyRepo.existsByEmailAndPassword(company.getEmail(), company.getPassword())) {
            Company company1 = companyService.findByEmail(company.getEmail());
            if (company1.getPassword().matches(company.getPassword())) {
                final UserDetails userDetails = companyService2.loadUserByUsername(company.getEmail());
                ResponseInJson responseInJson = new ResponseInJson();
                responseInJson.setCode(200);
                responseInJson.setHttpCode(200);
                responseInJson.setMessage("Success");
                final String jwt = JwtUtil.generateToken(userDetails);
                responseInJson.setJwt(jwt);
                responseInJson.setData(company1);
                return ResponseEntity.ok(responseInJson);

            } else return ResponseEntity.badRequest().body("wrong password");
        }
        return ResponseEntity.badRequest().body("Something went wrong");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody Company company){
        return null;
    }
}
