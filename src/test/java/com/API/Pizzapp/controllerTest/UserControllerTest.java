package com.API.Pizzapp.controllerTest;

import com.API.Pizzapp.Controller.PurchaseControllerImp;
import com.API.Pizzapp.Security.JwtAuthenticationFilter;
import com.API.Pizzapp.Services.JwtService;
import com.API.Pizzapp.Services.PurchaseService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserControllerTest {

    @InjectMocks
    private PurchaseControllerImp purchaseControllerImp;

    @Mock
    private PurchaseService purchaseService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private HttpServletRequest request;

    @Mock
    JwtService jwtService;

    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;


}
