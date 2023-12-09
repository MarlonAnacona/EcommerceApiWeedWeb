package com.API.Pizzapp.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPurchaseItemDTO {
    private Long id;
    private ProductDTO product;
    private int quantity;
    private double priceAtPurchase;
}
