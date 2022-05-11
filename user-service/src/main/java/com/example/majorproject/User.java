package com.example.majorproject;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder


public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int  id ;


    @Column(unique = true)
    private String  userId;


    private int age = 12;

    private String email;

    private String phone;

    private String authority;
    private String password;


}
