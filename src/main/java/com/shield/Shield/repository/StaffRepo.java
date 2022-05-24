package com.shield.Shield.repository;

import com.shield.Shield.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StaffRepo extends JpaRepository<Staff , UUID> {

//  boolean existsByEmailAndPassword(String email , String password);

}
