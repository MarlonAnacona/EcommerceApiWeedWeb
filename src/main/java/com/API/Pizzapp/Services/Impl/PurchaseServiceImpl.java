package com.API.Pizzapp.Services.Impl;


import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Repository.ProductRepository;
import com.API.Pizzapp.Repository.PurchaseItemRepository;
import com.API.Pizzapp.Repository.PurchaseRepository;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {



    private final PurchaseRepository purchaseRepository;

    private final PurchaseItemRepository purchaseItemRepository;


    private EmailServiceImpl emailService;

    private ProductRepository productRepository;
    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, PurchaseItemRepository purchaseItemRepository, EmailServiceImpl emailService, ProductRepository productRepository) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.emailService = emailService;
        this.productRepository = productRepository;
    }



    public PurchaseEntity createPurchase(String email, PurchaseDTO purchaseDTO) throws Exception {
        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.setUserId(purchaseDTO.getUserId());
        purchaseEntity.setDateOfPurchase(purchaseDTO.getDateOfPurchase());

        purchaseEntity = purchaseRepository.save(purchaseEntity);


        for (PurchaseItemDTO itemDTO : purchaseDTO.getItems()) {
            PurchaseItemEntity itemEntity = new PurchaseItemEntity();

            // Buscar el producto asociado usando su ID
            ProductEntity product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new Exception("Producto no encontrado con ID: " + itemDTO.getProductId()));


            int newQuantity = product.getQuantity() - itemDTO.getQuantity();
            if (newQuantity < 0) {
                throw new Exception("No hay suficiente cantidad del producto  " + product.getName());
            }
            itemEntity.setProduct(product);

            itemEntity.setQuantity(itemDTO.getQuantity());
            itemEntity.setPriceAtPurchase(itemDTO.getPriceAtPurchase());

            itemEntity.setPurchase(purchaseEntity);

            product.setQuantity(newQuantity);
            productRepository.save(product);

            purchaseItemRepository.save(itemEntity);
        }

        purchaseEntity.setItems(purchaseItemRepository.findByPurchase(purchaseEntity));
        sendVerificationCode(email, purchaseEntity.getId());

        return purchaseEntity;
    }


    // Método para obtener compras de un usuario
    public List<GetPurchaseDTO> getPurchaseById(Long userId) {
        Optional<PurchaseEntity> purchases = purchaseRepository.findByUserId(userId);

        // Convertir cada PurchaseEntity a PurchaseDTO
        return purchases.stream()
                .map(this::convertToPurchaseDTO)
                .collect(Collectors.toList());
    }


    public String sendVerificationCode(String email, Long code) throws Exception {

        return emailService.sendEmail(
                    email,
                    "Tu número de compra: ",
                     code
            );

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
