package com.shield.Shield.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Driver {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id ;
    private String fullName ;
    private String email ;
    private String password ;
    private String phoneNumber ;
    private String altPhoneNumber ;
    private String dob ;
    private String hireDate ;
    private String passportExpDate ;
    private String hazmatDate ;
    private String licenseNumber ;
    private String state ;
    private String zipCode ;
    private String address ;
    private int status ;
    private String city ;
    private String emergencyPhoneNumber ;

}
