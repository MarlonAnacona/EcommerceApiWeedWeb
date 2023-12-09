package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.PurchaseDTO;
import com.API.Pizzapp.Models.PurchaseEntity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface PurchaseControllerI {


    public ResponseEntity<?> createPurchase(HttpServletRequest request, @RequestBody PurchaseDTO purchaseDTO, @RequestHeader("email") String email);
    public ResponseEntity<?> getPurchases(HttpServletRequest request);

}