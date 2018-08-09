package com.liberty.doshka.model;

import lombok.Data;

import java.util.Set;

@Data
public class Article {

    private String title;
    private String description;
    private String text;
    private Set<String> tags;
    private long userId;

}
