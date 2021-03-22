package com.lamvanhai.entity;

import com.lamvanhai.annotation.Column;
import com.lamvanhai.annotation.Entity;
import com.lamvanhai.annotation.Id;

@Entity(name = "collar_Stype")
public class Collar_Stype {
    @Id(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
}
