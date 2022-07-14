package com.capstone.fgd.security;

import com.capstone.fgd.domain.dao.Users;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${token.expired.hour:1}")
//    @Value("3600000")
    private Long expiredHour;

    public String generateToken(Authentication authentication){
        final Users user = (Users) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() * expiredHour);
        //(expiredHour * 3600000)

        Map<String,Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("isAdmin", user.getIsAdmin());

        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token) throws Exception{
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex){
            log.error("Invalid Jwt Signature : {}", ex.getMessage());
        } catch (MalformedJwtException ex){
            log.error("Invalid Jwt Token : {}", ex.getMessage());
        } catch (ExpiredJwtException ex){
            log.error("Invalid Jwt Token : {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported Jwt Token : {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Unsupported Jwt Token : {}", ex.getMessage());
        }
        return false;
    }


    public String getEmail(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("email").toString();
    }
}
