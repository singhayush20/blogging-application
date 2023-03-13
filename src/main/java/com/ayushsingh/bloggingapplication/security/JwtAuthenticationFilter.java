package com.ayushsingh.bloggingapplication.security;

import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.exceptions.InvalidTokenInHeaderException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // to enable autowiring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String CLASS_NAME = JwtAuthenticationFilter.class.getName();
    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

   

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            // Step 1: Get authorization header
            final String authorizationHeader = request.getHeader(AppConstants.AUTHORIZATION);
            System.out.println(CLASS_NAME + " request header token: " +
                    authorizationHeader);

            // Step 2: Extract username and token
            String username = null, jwtToken = null;
            if (authorizationHeader != null &&
                    authorizationHeader.startsWith(AppConstants.BEARER)) {

                jwtToken = authorizationHeader.substring(7);
                /*
                 * Handle following errors for extractUsername method
                 * ExpiredJwtException,
                 * UnsupportedJwtException,
                 * MalformedJwtException,
                 * SignatureException,
                 * IllegalArgumentException
                 */
                username = this.jwtTokenHelper.extractUsername(jwtToken);
                // Extracted username
                System.out.println("Username: " + username);
                System.out.println("getAuthentication: " +
                        SecurityContextHolder.getContext().getAuthentication());
                // Step 3: Check if username is not null and getAuthentication gives null
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Step 4: Extract the userDetails
                    final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);

                    // Step 5: Validate the jwt token with user details
                    if (this.jwtTokenHelper.validateToken(jwtToken, userDetails)) {
                        // Step 6: If token is valid set authentication in security context holder
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    } else {
                        System.out.println(CLASS_NAME + " jwt token validation returned false");
                    }
                } else {
                    exceptionResolver.resolveException(request, response, null,
                            new InvalidTokenInHeaderException(
                                    "username is null: " + (username == null) + " authentication in context is null: "
                                            + (SecurityContextHolder.getContext().getAuthentication())));
                }
            } else {
                System.out.println("Header token is null or does not start with BEARER");
            }
            // Step 7: Proceed
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException("Token is expired"));
        } catch (UnsupportedJwtException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (MalformedJwtException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (SignatureException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (InvalidTokenInHeaderException e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }
 
}
