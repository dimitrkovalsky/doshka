package com.liberty.doshka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Document(collection = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String encryptedPassword;

}
