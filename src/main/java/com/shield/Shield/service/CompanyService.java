package com.shield.Shield.service;

import com.shield.Shield.entity.Company;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CompanyService extends UserDetailsService {

    Company addDetails(Company company);

    Company findByEmail(String email);


}
