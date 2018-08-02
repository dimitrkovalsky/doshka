package com.liberty.doshka.model;

import lombok.Data;

@Data
public class User {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int telephone;
}
