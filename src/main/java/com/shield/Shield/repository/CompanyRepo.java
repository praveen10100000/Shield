package com.shield.Shield.repository;

import com.shield.Shield.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company , Integer> {

    Company findByEmail(String Email);

    boolean existsByEmailAndPassword(String email , String password);

    Company findByEmailAndPassword(String email , String password);

    public Company findByResetPasswordToken(String token);
}
