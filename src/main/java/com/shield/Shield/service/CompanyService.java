package com.shield.Shield.service;

import com.shield.Shield.entity.Company;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompanyService extends UserDetailsService {

    Company addDetails(Company company);

    Company findByEmail(String email);

    public int generateOtp();


    void sendMail(String sub, String from, String to, String content);

    Company findByResetPasswordToken(String resetPasswordToken);

    Company getCompanyDataFromToken();

    public void save(MultipartFile file);

    public void init();
}
