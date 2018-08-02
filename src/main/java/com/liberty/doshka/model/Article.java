package com.liberty.doshka.model;

import lombok.Data;

import java.util.List;

@Data
public class Article {

    private String title;
    private String description;
    private List<Image> images;
    private User user;

}
