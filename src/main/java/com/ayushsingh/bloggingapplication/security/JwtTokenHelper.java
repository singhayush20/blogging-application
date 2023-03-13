package com.ayushsingh.bloggingapplication.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.exceptions.InvalidTokenInHeaderException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
    public String extractUsername(String token) {
        String subject = extractClaim(token, Claims::getSubject);
        System.out.println("Extracted subject: " + subject);
        return subject;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        /*
         * The parseClaimsJws() method throws following exceptions-
         * ExpiredJwtException,
         * UnsupportedJwtException,
         * MalformedJwtException,
         * SignatureException,
         * IllegalArgumentException
         * If the token is expired, ExpiredJwtException will be thrown
         */
        Claims parsedClaims = Jwts.parser().setSigningKey(AppConstants.SECRET_KEY).parseClaimsJws(token).getBody();
        System.out.println("Parsed Claims: " + parsedClaims.getSubject());
        return parsedClaims;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date issueDate = new Date(System.currentTimeMillis());
        System.out.println("issueDate: " + issueDate + " time: " + issueDate.getTime() + " issueDate formatted: "
                + issueDate.toString());
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10 
        );
        System.out.println("Expiration date: " + expirationDate + " formatted: " + expirationDate.toString());
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, AppConstants.SECRET_KEY).compact();
    }

  

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isUsernameValid = username.equals(userDetails.getUsername());
        boolean isJwtTtokenExpired = isTokenExpired(token);
        System.out.println("Is token expired: " + isJwtTtokenExpired + " is username valid: " + isUsernameValid);
        if (isUsernameValid == false) {
            throw new InvalidTokenInHeaderException("Username in the token is invalid");
        }
        if (isJwtTtokenExpired == true) {
            throw new InvalidTokenInHeaderException("Token is expired!");
        }
        return (isUsernameValid && !isJwtTtokenExpired);
    }

}
