package com.restaurant.a3.RestaurantApi.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

        if(token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
            log.info("O token está nulo, vazio ou nao comeca com o cabecalho bearer.");

            filterChain.doFilter(request,response);
            return;
        }

        if(!JwtUtils.isValidToken(token)) {
            log.warn("O token é inválido ou já foi expirado!");

            filterChain.doFilter(request,response);
            return;
        }

        String username = JwtUtils.getUsernameFromToken(token);

        toAuthentication(request, username);
        filterChain.doFilter(request, response);
    }

    private void toAuthentication(HttpServletRequest request, String username) {
        UserDetails details = detailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken
                        .authenticated(details, null, details.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
