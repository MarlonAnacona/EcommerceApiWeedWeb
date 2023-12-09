package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.ProductDTO;
import com.API.Pizzapp.Models.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productEntity);
    Optional<ProductDTO> getProductById(Long id);
    List<ProductDTO> getAllProducts();

    public List<ProductDTO> getProductsByCategory(Long categoryId);
}
