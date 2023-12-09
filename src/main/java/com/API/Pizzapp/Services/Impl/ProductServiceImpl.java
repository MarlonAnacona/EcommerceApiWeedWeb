package com.API.Pizzapp.Services.Impl;

import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Repository.CategoryRepository;
import com.API.Pizzapp.Repository.ProductRepository;
import com.API.Pizzapp.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(productDTO.getName());
        productEntity.setCode(productDTO.getCode());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setProfilePicture(productDTO.getProfilePicture());
        productEntity.setPrice(productDTO.getPrice());
        productEntity.setQuantity(productDTO.getQuantity());
        productEntity.setRating(productDTO.getRating());

        if (productDTO.getCategory()!=0) {
            Optional<CategoryEntity> category = categoryRepository.findById((long) productDTO.getCategory());
            category.ifPresent(productEntity::setCategory);
        }

         productRepository.save(productEntity);
        return  productDTO;
    }


    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productEntity.getId());
        productDTO.setName(productEntity.getName());
        productDTO.setCode(productEntity.getCode());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setProfilePicture(productEntity.getProfilePicture());
        productDTO.setPrice(productEntity.getPrice());
        productDTO.setQuantity(productEntity.getQuantity());
        productDTO.setRating(productEntity.getRating());

        if (productEntity.getCategory() != null) {
            productDTO.setCategory(Math.toIntExact(productEntity.getCategory().getId()));
        }
        return productDTO;
    }





    public GetPurchaseItemDTO convertToPurchaseItemDTO(PurchaseItemEntity itemEntity) {
        GetPurchaseItemDTO itemDTO = new GetPurchaseItemDTO();
        itemDTO.setId(itemEntity.getId());
        itemDTO.setProduct(convertToDTO(itemEntity.getProduct()));
        itemDTO.setQuantity(itemEntity.getQuantity());
        itemDTO.setPriceAtPurchase(itemEntity.getPriceAtPurchase());
        return itemDTO;
    }

    public GetPurchaseDTO convertToPurchaseDTO(PurchaseEntity purchaseEntity) {
        GetPurchaseDTO purchaseDTO = new GetPurchaseDTO();
        purchaseDTO.setId(purchaseEntity.getId());
        purchaseDTO.setUserId(purchaseEntity.getUserId());
        purchaseDTO.setDateOfPurchase(purchaseEntity.getDateOfPurchase());
        purchaseDTO.setItems(purchaseEntity.getItems().stream()
                .map(this::convertToPurchaseItemDTO)
                .collect(Collectors.toList()));
        return purchaseDTO;
    }

}
