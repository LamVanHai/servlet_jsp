package com.lamvanhai.entity;

import com.lamvanhai.annotation.Column;
import com.lamvanhai.annotation.Entity;
import com.lamvanhai.annotation.Id;

import java.sql.Timestamp;

@Entity(name = "blog")
public class Blog {
    @Id(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name = "created_By")
    private String created_By;
    @Column(name = "create_Date")
    private Timestamp create_Date;
}
