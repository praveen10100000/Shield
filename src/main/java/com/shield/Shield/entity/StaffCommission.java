package com.shield.Shield.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StaffCommission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;
    private String proType;
    private int status  ;
    private  String grossPercentage ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id" , name = "Staff_id")
    private Staff staff ;


}
