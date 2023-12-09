package com.API.Pizzapp.Controller;

import com.API.Pizzapp.Models.ProductDTO;
import com.API.Pizzapp.Models.ProductEntity;
import com.API.Pizzapp.Models.ResponseDTO;
import com.API.Pizzapp.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/Product")
@CrossOrigin("*")
public class ProductControllerImpl implements ProductControllerI {

    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productEntity) {
        try {
            ProductDTO createdProduct = productService.createProduct(productEntity);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO("Error al crear el producto: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            Optional<ProductDTO> product = productService.getProductById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO("Error al obtener el producto: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getProducts")
    public ResponseEntity getAllProducts() {
        try {
            List<ProductDTO> product = productService.getAllProducts();
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO("Error al obtener los productos: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }    }

    @GetMapping("/getProductCategories/{id}")
    public ResponseEntity getAllProducts(@PathVariable Long id) {
        try {
            List<ProductDTO> product = productService.getProductsByCategory(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            ResponseDTO responseDTO = new ResponseDTO("Error al obtener los  productos: " + e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }    }
}
