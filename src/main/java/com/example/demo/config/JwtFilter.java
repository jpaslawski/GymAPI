package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!request.getRequestURI().contains("/authenticate") && !request.getRequestURI().contains("/create")) {
            final String header = request.getHeader("Authorization");
            System.out.println(header);
            UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
            if (authResult == null) {
                throw new ServletException("This request is not authorized!");
            }

            SecurityContextHolder.getContext().setAuthentication(authResult);
        }

        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {

        // Parse token trough signing key
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("iFuZc|_6D{UBn(A".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(header.replace("Bearer ", ""));

        if(claimsJws == null) {
            return null;
        }

        // Get username and user permissions from token
        String email = claimsJws.getBody().get("email").toString();
        String permissions = claimsJws.getBody().get("permissions").toString();

        //
        Set<SimpleGrantedAuthority> role = Collections.singleton(new SimpleGrantedAuthority(permissions));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, role);

        return usernamePasswordAuthenticationToken;
    }
}
