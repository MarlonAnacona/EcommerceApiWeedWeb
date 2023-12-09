package com.API.Pizzapp.Services;

import com.API.Pizzapp.Models.GetPurchaseDTO;
import com.API.Pizzapp.Models.PurchaseDTO;
import com.API.Pizzapp.Models.PurchaseEntity;

import java.util.List;
import java.util.Optional;


public interface PurchaseService {


    public PurchaseEntity createPurchase(String id, PurchaseDTO purchaseDTO) throws Exception;

    public List<GetPurchaseDTO> getPurchaseById(Long id);


}
