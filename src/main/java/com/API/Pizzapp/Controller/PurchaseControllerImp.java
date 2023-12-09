package com.API.Pizzapp.Controller;


import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Security.JwtAuthenticationFilter;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/Purchase")
@CrossOrigin("*")
public class UserControllerImp implements  UserControllerI{


    PurchaseService purchaseService;

    JwtAuthenticationFilter jwtAutorizationFilter;

    JwtService jwtService;
    @PostMapping("/createPurchase")
    public ResponseEntity<?> createPurchase(HttpServletRequest request, @RequestBody PurchaseEntity purchaseDTO) {
        try {
            String token = jwtAutorizationFilter.getTokenFromRequest(request);
            String email = jwtService.getUsernameFromToken(token);
            PurchaseEntity createdPurchase = purchaseService.createPurchase(email, purchaseDTO);
            return new ResponseEntity<>(createdPurchase, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponse("Error al crear la compra: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para consultar las compras de un usuario
    @GetMapping("/getPurchases")
    public ResponseEntity<?> getPurchases(HttpServletRequest request) {
        try {
            String token = jwtAutorizationFilter.getTokenFromRequest(request);
            String email = jwtService.getUsernameFromToken(token);
            List<PurchaseEntity> userPurchases = purchaseService.getPurchasesByEmail(email);
            return new ResponseEntity<>(userPurchases, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponse("Error al obtener las compras: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
