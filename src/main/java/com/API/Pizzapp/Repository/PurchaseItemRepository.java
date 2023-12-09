package com.API.Pizzapp.Repository;

import com.API.Pizzapp.Models.PurchaseEntity;
import com.API.Pizzapp.Models.PurchaseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseItemRepository  extends JpaRepository<PurchaseItemEntity,Long> {

    List<PurchaseItemEntity> findByPurchase(PurchaseEntity purchase);

}
