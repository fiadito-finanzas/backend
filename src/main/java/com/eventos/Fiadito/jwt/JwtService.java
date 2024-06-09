package com.eventos.Fiadito.jwt;

import com.eventos.Fiadito.dtos.AuthResponseDTO;
import com.eventos.Fiadito.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET_KEY = "d5165a1wd61a6sca8c16as51da+d4sa56d1sa6ca52d1321";

    public String getToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String generateToken(SecurityUser securityUser){
        Map<String, Object> claims = new HashMap<>();
        Object user_id = securityUser.getUser().getId();
        Object roles = securityUser.getAuthorities().stream().collect(Collectors.toList()).get(0);
        claims.put("roles", roles);
        claims.put("user_id", user_id);
        return getToken(securityUser.getUsername(), claims);
    }
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public boolean verify(AuthResponseDTO token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

}
