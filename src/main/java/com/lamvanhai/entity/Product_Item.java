package com.lamvanhai.entity;

import com.lamvanhai.annotation.Column;
import com.lamvanhai.annotation.Entity;
import com.lamvanhai.annotation.Id;

@Entity(name = "product_item")
public class Product_Item {
    @Id(name = "id")
    private int id;
    @Column(name = "product_id")
    private int product_id;
    @Column(name = "size_id")
    private int size_id;
    @Column(name = "color_id")
    private int color_id;
    @Column(name = "form_id")
    private int form_id;
    @Column(name = "collar_stype_id")
    private int collar_stype_id;
    @Column(name = "material_id")
    private int material_id;
    @Column(name = "steeve_stype_id")
    private int steeve_stype_id;

    private int vignette_id;
    private int species_id;
    private int trourse_stype_id;
    private int number;
}
