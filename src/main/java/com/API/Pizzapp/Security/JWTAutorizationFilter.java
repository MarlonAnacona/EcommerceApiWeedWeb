package com.API.Pizzapp.Security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JWTAutorizationFilter extends UsernamePasswordAuthenticationFilter {




    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("email");
        String password = request.getParameter("password");

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }


}