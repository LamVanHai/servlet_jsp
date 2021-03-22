package com.lamvanhai.entity;

import com.lamvanhai.annotation.Column;
import com.lamvanhai.annotation.Entity;

@Entity(name = "blog_tag")
public class Blog_Tag {
    @Column(name = "blog_Id")
    private int blog_Id;
    @Column(name = "tag_Id")
    private int tag_Id;
}
