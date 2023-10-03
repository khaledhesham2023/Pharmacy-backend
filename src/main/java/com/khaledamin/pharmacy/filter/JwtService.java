package com.khaledamin.pharmacy.filter;

import com.khaledamin.pharmacy.user.UserAuthorityRepo;
import com.khaledamin.pharmacy.user.UserEntity;
import com.khaledamin.pharmacy.user.UserRoleRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET_KEY = "JSqxiiOmahAcRPVCWN4IrTqSMNe4qHbfOn+EIUpFMlI94XF4LJb+TrPgGjpaPeab";

    public String generateUsernameFromToken(String token) {
        return extractFromClaim(token, Claims::getSubject);
    }

    private <T> T extractFromClaim(String token, Function<Claims, T> resolveClaims) {
        final Claims claims = extractAllClaims(token);
        return resolveClaims.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public boolean isTokenValid(String token, UserEntity user) {
        final String username = generateUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractFromClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserEntity user) {
        return createToken(user, new HashMap<>());
    }

    private String createToken(UserEntity user, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}

