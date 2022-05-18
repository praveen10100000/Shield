package com.shield.Shield.repository;

import com.shield.Shield.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Integer> {

    Driver findByEmail(String email);
}
