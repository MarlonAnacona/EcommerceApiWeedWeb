package com.API.Pizzapp.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private Long userId; // Suponiendo que tienes un ID de usuario en lugar de un email
    private LocalDateTime dateOfPurchase;
    private List<PurchaseItemDTO> items;
}
