package com.API.Pizzapp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {


    private Long id;

    private String code;

    private String name;

    private String description;

    private  byte[] profilePicture;

    private BigDecimal price;


    private int category;


    private Integer quantity;



    private Integer rating;

}
