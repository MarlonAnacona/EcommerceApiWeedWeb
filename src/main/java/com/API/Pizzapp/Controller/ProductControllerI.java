package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.ProductDTO;
import com.API.Pizzapp.Models.ProductEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductControllerI {

    ResponseEntity<?> createProduct(@RequestBody ProductDTO productEntity);
    ResponseEntity<?> getProduct(@PathVariable Long id);


    ResponseEntity getAllProducts();
}
