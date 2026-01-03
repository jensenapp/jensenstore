package com.eazybytes.jensenstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name",nullable = false,length = 250)
    private String name;

    @Column(name = "description",nullable = false,length = 500)
    private String description;

    @Column(name = "price",nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(name = "popularity",nullable = false)
    private Integer popularity;

    @Column(name = "image_url",length = 500)
    private String image_url;

    @Column(name = "created_at",nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Instant created_at;

    @Column(name = "created_by",nullable = false,length = 20)
    private String created_by;

    @ColumnDefault("NULL")
    @Column(name = "updated_at")
    private  Instant updated_at;

    @ColumnDefault("NULL")
    @Column(name = "updated_by",length = 20)
    private String updated_by;
}
