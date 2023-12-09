package com.API.Pizzapp.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemDTO {
    private Long productId; // ID del producto
    private int quantity;
    private double priceAtPurchase;
}
