package com.shield.Shield.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordChange {

    private String oldPassword ;
    private String newPassword ;

}
