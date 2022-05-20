package com.shield.Shield.serviceImpl;

import com.shield.Shield.entity.Company;
import com.shield.Shield.repository.CompanyRepo;
import com.shield.Shield.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.CoderMalfunctionError;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
public class CompanyServiceImp implements CompanyService {

    private final Path root  = Paths.get("uploads");

    @Autowired
    private JavaMailSender javaMailSender ;

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

    public Company findByResetPasswordToken(String resetPasswordToken){
        return companyRepo.findByResetPasswordToken(resetPasswordToken);
    }

    @Override
    public Company getCompanyDataFromToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = null;
        if(principal instanceof UserDetails){
            id = ((UserDetails)principal).getUsername();
        }else{
            id = principal.toString();
        }
        Company company = companyRepo.getById(UUID.fromString(id));
        return company ;

    }

    @Override
    public void save(MultipartFile file) {
       try{
           Files.copy(file.getInputStream() , this.root.resolve(file.getOriginalFilename()));
       }catch (Exception e){
           throw new RuntimeException("Could not upload the files . Error :" + e.getMessage());
       }
    }

    @Override
    public void init() {
        try{
            Files.createDirectory(root);
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            throw new RuntimeException("Could not initialize folder for upload");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) {
        try {
            Company company = companyRepo.findById(UUID.fromString(uuid)).get();
            return new org.springframework.security.core.userdetails.User(uuid, company.getPassword(), new ArrayList<>());
        }catch (Exception ex){
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }

     public int generateOtp(){
         Random random = new Random();
         int otp = 100000 + random.nextInt(90000);
         return otp;
     }

    public void sendMail(String subject, String From, String To, String Content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.addTo(To);
            mimeMessageHelper.setFrom(From);
            mimeMessageHelper.setText(Content);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }



}



