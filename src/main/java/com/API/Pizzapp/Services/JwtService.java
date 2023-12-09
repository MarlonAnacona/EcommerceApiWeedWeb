package com.API.Pizzapp.Services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY="586E3272357538782F413F4428472B4B6250655368566B597033733676397924";


    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getUsernameFromToken(String token) {
        return getClaim(token, claims -> claims.get("userId", Long.class));
    }
    public boolean isTokenValid(String token ) {
        return (!isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    public Long getUserIdFromToken(String token) {
        Map<String, Object> payloadMap = decodePayloadToMap(token);
        if (payloadMap != null) {
            return ((Number) payloadMap.get("userId")).longValue();
        }
        return null;
    }

    public boolean isTokenExpired(String token) {
        Map<String, Object> payloadMap = decodePayloadToMap(token);
        if (payloadMap != null) {
            Date expiration;
            Object expObj = payloadMap.get("exp");
            if (expObj instanceof Integer) {
                expiration = new Date(((Integer) expObj).longValue() * 1000);
            } else if (expObj instanceof Long) {
                expiration = new Date((Long) expObj * 1000);
            } else {
                return true; // Si no se puede determinar la fecha de expiración, considera el token como expirado
            }
            return expiration.before(new Date());
        }
        return true;
    }

    private Map<String, Object> decodePayloadToMap(String token) {
        String payload = decodePayload(token);
        if (payload != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(payload, Map.class);
            } catch (IOException | JsonProcessingException e) {
                e.printStackTrace(); // Manejo de la excepción
            }
        }
        return null;
    }

    private String decodePayload(String token) {
        String[] parts = token.split("\\.");
        if (parts.length == 3) {
            byte[] decoded = Base64.getUrlDecoder().decode(parts[1]);
            return new String(decoded, StandardCharsets.UTF_8);
        }
        return null;
    }

}
