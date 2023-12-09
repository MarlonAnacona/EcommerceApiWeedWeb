package com.API.Pizzapp.Repository;

import com.API.Pizzapp.Models.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {


    @Query("SELECT p FROM PurchaseEntity p WHERE p.userId = :userId")
    Optional<PurchaseEntity> findByUserId(Long userId);


}
