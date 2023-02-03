package com.ayushsingh.bloggingapplication.security;

import java.io.IOException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // to enable autowiring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String CLASS_NAME = JwtAuthenticationFilter.class.getName();
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    /*
     * This method will be called everytime the APIs are
     * hit by the user
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get token
        // key is "Authorization" -get its value
        String requestToken = request.getHeader("Authorization");
        // token starts from Bearer <token value>
        System.out.println(CLASS_NAME + " Token in request: " + requestToken);
        // fetch the username
        String username = null;

        String token = null;

        if (requestToken != null && requestToken.startsWith("Bearer")) {

            // we found token
            token = requestToken.substring(7);// Bearer 44894732e8732
            System.out.println("Token obtained as: " + token);
            try {
                username = this.jwtTokenHelper.getUsernameFromToken(token);
                System.out.println("Username obtained as: " + username);
            } catch (IllegalArgumentException e) {
                System.out.println(CLASS_NAME + " Unable to get JWT Token: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.println(CLASS_NAME + " Jwt Token has expried: " + e.getMessage());
            } catch (MalformedJwtException e) {
                System.out.println(CLASS_NAME + " Malformed Jwt token: " + e.getMessage());
            }
        } else {
            System.out.println(CLASS_NAME + " Jwt token does not begin with \"Bearer\" ");
        }

        // once we get the token, now validate
        if (
        // username should not be null
        username != null &&
        // also, Spring security should not be authenticating any user
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // this means the token is valid
                // create an authentication instance
                System.out.println(username + " Granted authorities for the user: " + userDetails.getAuthorities());
                // userDetails.getAuthorities() gives the list of all the granted authorities
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // set the details
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // now authentication using SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("Authentication is set for " + username);

            } else {
                System.out.println(CLASS_NAME + " Invalid jwt token");
            }
        } else {
            System.out.println(
                    CLASS_NAME + " Username is null: " + username + " or authentication context is not null");
        }
        filterChain.doFilter(request, response);
        System.out.println("Returning from doFilterInternal() for: " + username);
    }

 
}
