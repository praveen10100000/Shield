package com.shield.Shield.controller;

import com.shield.Shield.ResponseInJson;
import com.shield.Shield.dto.OtpVerification;
import com.shield.Shield.dto.PasswordChange;
import com.shield.Shield.entity.Company;
import com.shield.Shield.otp.OtpDetails;
import com.shield.Shield.repository.CompanyRepo;
import com.shield.Shield.service.CompanyService;
import com.shield.Shield.serviceImpl.CompanyServiceImp;
import com.shield.Shield.util.JwtUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("api/shield")
public class CompanyController {

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyServiceImp companyService2;

    public static Map<String, Object> otpInCache = new HashMap<>();

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

                final UserDetails userDetails = companyService2.loadUserByUsername(company1.getId().toString());

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


    public void sendOtp(String otp, String email) {
        String content = "This is your otp " + otp;
        companyService.sendMail("your OTP is here", "praveenpandey.uttarakhand@gmail.com", email, content);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody Company company) {
        Company company1 = companyService.findByEmail(company.getEmail());
        if (company1 != null) {
            OtpDetails otpDetails = new OtpDetails();
            otpDetails.setOtp(companyService.generateOtp());
            System.out.print(otpDetails);
            otpInCache.put(String.valueOf(company1.getId()), otpDetails);
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.submit(() -> {
                System.out.println("data is everywhere");
                sendOtp(String.valueOf(otpDetails.getOtp()), company1.getEmail());
            });
            executor.shutdown();
            return ResponseEntity.ok("OTP send in email");
        }
        return ResponseEntity.ok("company is not found");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody OtpVerification otpVerification){
        Company company1 = companyService.findByEmail(otpVerification.getEmail());
        OtpDetails otpDetails = (OtpDetails) otpInCache.get(company1.getId().toString());
        System.out.println(otpDetails.getOtp()+" "+ otpVerification.getOtp());
        if ( otpDetails.getOtp()==otpVerification.getOtp()){
            ResponseInJson responseInJson  = new ResponseInJson();
            responseInJson.setCode(200);
            responseInJson.setHttpCode(200);
            responseInJson.setMessage("Success");
            String token = RandomString.make(30);
            company1.setResetPasswordToken(token);
            responseInJson.setData(token);
            companyService.addDetails(company1);
            return ResponseEntity.ok(responseInJson);
        }

          return ResponseEntity.ok("OTP is not correct");
    }

    @PostMapping("/save-new-password")
    public ResponseEntity<?> saveNewPassword(@RequestBody Company company){
        Company company1 = companyService.findByResetPasswordToken(company.getResetPasswordToken());
        company1.setPassword(company.getPassword());
        ResponseInJson responseInJson = new ResponseInJson();
        responseInJson.setCode(200);
        responseInJson.setHttpCode(200);
        responseInJson.setMessage("Success");
        companyService.addDetails(company1);
        responseInJson.setData(company1);
        return  ResponseEntity.ok(responseInJson);

    }

    @PostMapping("/update-company-details")
    public ResponseEntity<?> updateCompanyDetails(@RequestBody Company company){
         Company company1 = companyService.getCompanyDataFromToken();
         company1.updateCompanyDetails(company);
         companyService.addDetails(company1);
         ResponseInJson responseInJson = new ResponseInJson();
         responseInJson.setCode(200);
         responseInJson.setHttpCode(200);
         responseInJson.setMessage("Success");
         responseInJson.setData(company1);
         return ResponseEntity.ok(responseInJson);
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChange passwordChange) {
        Company company = companyService.getCompanyDataFromToken();
        if (company.getPassword().matches(passwordChange.getOldPassword())) {
            company.setPassword(passwordChange.getNewPassword());

            ResponseInJson responseInJson = new ResponseInJson();
            responseInJson.setCode(200);
            responseInJson.setHttpCode(200);
            responseInJson.setMessage("Success");
            responseInJson.setData(company);
            companyService.addDetails(company);
            return ResponseEntity.ok(responseInJson);
        } else {
            return ResponseEntity.ok("Old password is incorrect");
        }
    }

    @PostMapping("/upload-company-documents")
    public ResponseEntity<ResponseInJson> uploadFiles (@RequestAttribute("files") MultipartFile[] files){
        companyService.getCompanyDataFromToken(); //for verified with token
        String message = "" ;
         try {
             List<String> filesname = new ArrayList<>();
             Arrays.asList(files).stream().forEach(file -> {
                 System.out.println(file.getOriginalFilename());
                 companyService.save(file);
                 filesname.add(file.getOriginalFilename());
             });
             ResponseInJson responseInJson = new ResponseInJson() ;
             responseInJson.setCode(200);
             responseInJson.setHttpCode(200);
             responseInJson.setMessage("Success" + filesname);
//            System.out.println(responseInJson.toString());
             return ResponseEntity.ok(responseInJson);
         }
                catch (Exception e) {
             System.out.println(e.getLocalizedMessage());
                message = "Fail to uploads file";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseInJson(message));
            }

    }


}