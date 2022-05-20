package com.shield.Shield.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Company {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id ;
    private String companyName ;
    private String mcNumber;
    private String dotNumber ;
    private String country ;
    private String state ;
    private String postalCode ;
    private String address ;
    private String city ;
    private String contactPerson ;
    private String phoneNumber ;
    private String email;
    private String password;
    private String resetPasswordToken ;

    public void updateCompanyDetails(Company company){
        companyName = company.getCompanyName() !=null ? company.getCompanyName() : companyName;
        country = company.getCountry() !=null ? company.getCountry() : country ;
        state = company.getState() !=null ? company.getState() : state;
        mcNumber = company.getMcNumber() !=null ? company.getMcNumber(): mcNumber;
        dotNumber = company.getDotNumber() !=null ? company.getDotNumber() : dotNumber;
        postalCode = company.getPostalCode() !=null ? company.getPostalCode() : postalCode;
        address = company.getAddress() !=null ? company.getAddress() : address;
        city = company.getCity() !=null ? company.getCity() : city;
        contactPerson = company.getContactPerson() !=null ? company.getContactPerson() : contactPerson;
        phoneNumber = company.getPhoneNumber() !=null ? company.getPhoneNumber() : phoneNumber;
        email = company.getEmail() !=null ? company.getEmail() : email;
        password = company.getPassword() !=null ? company.getPassword() : password;
        resetPasswordToken = company.getResetPasswordToken() !=null ? company.getResetPasswordToken() : resetPasswordToken;
    }


    public <E> Company(String name, String password, ArrayList<E> es) {
    }
}
