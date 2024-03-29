package com.michael.walletapi.security;

import com.michael.walletapi.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class JwtProvider {
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Long expiration = (long) (1000 * 60 * 60);

    public String generateToken(Authentication authentication){
        final User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .setId(user.getId().toString()) // with claims, this will be replaced
                .setSubject(user.getUsername()) // with claims, this will be replaced
                .setClaims(claims)
                .setIssuedAt(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            log.error("Invalid Jwt Signature: {}", ex.getMessage());
        }catch (MalformedJwtException ex){
            log.error("Invalid Jwt Token: {}", ex.getMessage());
        }catch (ExpiredJwtException ex){
            log.error("Expired Jwt Token: {}", ex.getMessage());
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported Jwt Token: {}", ex.getMessage());
        }catch (IllegalArgumentException ex){
            log.error("Jwt claim string is empty: {}", ex.getMessage());
        }
        return false;
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

}
