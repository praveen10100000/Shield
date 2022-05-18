package com.shield.Shield.otp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OtpDetails {

    private int otp ;
    private LocalDateTime expire =LocalDateTime.now().plusMinutes(5);


}
