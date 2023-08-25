package com.esoft.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private UserDetailsService userDetailsService;
    private SecretKey key;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        if (key == null) {
            byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
            key = Keys.hmacShaKeyFor(keyBytes);
        }

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("AUTH", user.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        List<LinkedHashMap<String, String>> authList = claims.get("AUTH", List.class);
        Collection<GrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (Map<String, String> authMap : authList) {
            for (String authority : authMap.values()) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", simpleGrantedAuthorities);
    }

    public Claims extractAllClaims(String token) throws RuntimeException {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) throws RuntimeException {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
