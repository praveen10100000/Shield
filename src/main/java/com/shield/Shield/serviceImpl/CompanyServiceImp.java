package com.shield.Shield.serviceImpl;

import com.shield.Shield.entity.Company;
import com.shield.Shield.repository.CompanyRepo;
import com.shield.Shield.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;

@Service
public class CompanyServiceImp implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public Company addDetails(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public Company findByEmail(String email) {
        return companyRepo.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Company company = companyRepo.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(String.valueOf(company.getEmail()), company.getPassword(), new ArrayList<>());
    }

}



