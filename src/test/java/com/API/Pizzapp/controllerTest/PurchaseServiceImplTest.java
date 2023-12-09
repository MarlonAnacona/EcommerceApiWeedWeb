package com.API.Pizzapp.controllerTest;

import com.API.Pizzapp.Repository.PurchaseRepository;
import com.API.Pizzapp.Services.Impl.PurchaseServiceImpl;
import com.API.Pizzapp.Services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class PurchaseServiceImplTest {

    @InjectMocks
    private PurchaseServiceImpl userService;

    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.getToken(any(UserDetails.class))).thenReturn("someToken");

    }



}
