package com.example.netbankingapplication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@CrossOrigin
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {



        System.out.println("\n\n\nJwtAuthenticationFilter.doFilterInternal");

        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Check if the token is in the Authorization header and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);  // Remove "Bearer " prefix
            username = jwtUtil.extractUsername(token);  // Extract username from the token
            System.out.println("username: " + username);
            System.out.println("token: " + token);
            System.out.println("authorizationHeader: " + authorizationHeader);
        }

        // Validate the token and check if the user is already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the user details from the userDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            System.out.println("\n\n\nJwtAuthenticationFilter.doFilterInternal: token is valid and roles are " + userDetails.getAuthorities() + " " + userDetails.getUsername());

            // Validate the token
            if (jwtUtil.validateToken(token, userDetails)) {

                System.out.println("\n\n\nJwtAuthenticationFilter.doFilterInternal: token is valid and roles are " + userDetails.getAuthorities());
                // If token is valid, set authentication in the SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
