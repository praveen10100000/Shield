package com.shield.Shield.config;

import com.shield.Shield.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    @Autowired
    private Environment environment ;

    @Bean
    public JavaMailSender getMailSender(){

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl() ;
        javaMailSender.setHost(environment.getProperty("spring.mail.host"));
        javaMailSender.setPort(Integer.valueOf(environment.getProperty("spring.mail.port")));
        javaMailSender.setUsername(environment.getProperty("spring.mail.username"));
        javaMailSender.setPassword(environment.getProperty("spring.mail.password"));
        Properties javaMailproperties = new Properties();
        javaMailproperties.put("mail.smtp.starttls.enable", "true");
        javaMailproperties.put("mail.smtp.auth", "true");
        javaMailproperties.put("mail.transport.protocol", "smtp");
        javaMailproperties.put("mail.debug", "true");
        javaMailproperties.put("mail.smtp.ssl.trust", "*");

        javaMailSender.setJavaMailProperties(javaMailproperties);
        return javaMailSender ;


    }

}
