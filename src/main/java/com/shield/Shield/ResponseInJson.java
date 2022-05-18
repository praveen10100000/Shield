package com.shield.Shield;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseInJson<T> {
     private int httpCode ;
     private int code ;
     private String message ;
     @JsonInclude( value = JsonInclude.Include.NON_NULL)
     private String jwt;
     @JsonInclude( value = JsonInclude.Include.NON_NULL)
     private T data;
}
