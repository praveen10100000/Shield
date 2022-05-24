package com.shield.Shield.serviceImpl;

import com.shield.Shield.entity.Company;
import com.shield.Shield.entity.Staff;
import com.shield.Shield.repository.StaffRepo;
import com.shield.Shield.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StaffServiceImp implements StaffService {

    @Autowired
    private StaffRepo staffRepo ;

    @Override
    public Staff addDetails(Staff staff) {
       return staffRepo.save(staff);
    }

    public Staff getCompanyDataFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = null;
        if(principal instanceof UserDetails){
            id = ((UserDetails)principal).getUsername();
        }else{
            id = principal.toString();
        }
        Staff staff = staffRepo.getById(UUID.fromString(id));
        return staff ;

    }
}
