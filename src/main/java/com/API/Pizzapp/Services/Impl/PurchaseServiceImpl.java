package com.API.Pizzapp.Services.Impl;


import com.API.Pizzapp.Models.*;
import com.API.Pizzapp.Repository.PurchaseRepository;
import com.API.Pizzapp.Repository.VerificationCodeRepository;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceI {



    private final PurchaseRepository purchaseRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private VerificationCodeRepository codeRepository;

    private EmailServiceImpl emailService;

    @Autowired
    public UserServiceImpl(PurchaseRepository purchaseRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, VerificationCodeRepository codeRepository, EmailServiceImpl emailService) {
        this.purchaseRepository = purchaseRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.codeRepository = codeRepository;
        this.emailService = emailService;
    }



    public PurchaseEntity createPurchase(String email, PurchaseEntity purchaseDTO) throws Exception {


        return purchaseRepository.save(purchaseDTO);
    }

    // Método para obtener compras de un usuario
    public Optional<PurchaseEntity> getPurchasesByEmail(String email) throws Exception {


        return purchaseRepository.findByEmail(email);
    }


    public String sendVerificationCode(String email) throws Exception {
        UserEntity user = findUserBlock(email);

        return purchaseRepository.findByEmail(email).map(existingUser -> {
            CodeVerification code = new CodeVerification();
             code.setUser(existingUser);
             code.setCode(generateRandomCode());
             code.setExpiryDate(LocalDateTime.now().plusMinutes(15));
             code.setUsed(false);
            codeRepository.save(code);

            emailService.sendEmail(
                    email,
                    "Tu código de verificación",
                     code.getCode()
            );
        return  "Envio exitoso";
        }).orElse("No se encuentra el correo registrado");
    }


}
