package com.API.Pizzapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(length = 1024) // Asumiendo que la descripción puede ser larga
    private String description;

    @Column(nullable = false)
    private  byte[] profilePicture;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    @Column(nullable = false)
    private Integer quantity;



    @Column(nullable = false)
    private Integer rating;

}
