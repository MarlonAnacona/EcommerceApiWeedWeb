package com.API.Pizzapp.Controller;


import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Security.JwtAuthenticationFilter;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/Purchase")
@CrossOrigin("*")
public class PurchaseControllerImp implements PurchaseControllerI {


    PurchaseService purchaseService;

    JwtAuthenticationFilter jwtAutorizationFilter;

    JwtService jwtService;
@Autowired
    public PurchaseControllerImp(PurchaseService purchaseService, JwtAuthenticationFilter jwtAutorizationFilter, JwtService jwtService) {
        this.purchaseService = purchaseService;
        this.jwtAutorizationFilter = jwtAutorizationFilter;
        this.jwtService = jwtService;
    }

    @PostMapping("/createPurchase")
    public ResponseEntity createPurchase(HttpServletRequest request, @RequestBody PurchaseDTO purchaseDTO,@RequestHeader("email") String email) {
        try {
            ResponseDTO responseDTO = new ResponseDTO();
            String token= jwtAutorizationFilter.getTokenFromRequest(request);
            Long id= jwtService.getUserIdFromToken(token);
            purchaseDTO.setUserId(id);
            PurchaseEntity createdPurchase = purchaseService.createPurchase(email, purchaseDTO);
            responseDTO.setResponse("Compra realizada con éxito");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponse("Error al hacer la compra: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPurchases")
    public ResponseEntity  getPurchases(HttpServletRequest request) {
        try {
            String token = jwtAutorizationFilter.getTokenFromRequest(request);
            Long  id = jwtService.getUserIdFromToken(token);
            List<GetPurchaseDTO> userPurchases = purchaseService.getPurchaseById(id);
            return new ResponseEntity<>(userPurchases, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponse("Error al obtener las compras: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
