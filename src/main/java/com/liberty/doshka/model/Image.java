package com.liberty.doshka.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "image")
public class Image {

    @Id
    private String imageName;
    private String description;
    private String path;
}
