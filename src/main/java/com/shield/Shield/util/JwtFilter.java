package com.shield.Shield.util;

import com.shield.Shield.service.CompanyService;
import com.shield.Shield.serviceImpl.CompanyServiceImp;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Provider;

@Service
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CompanyServiceImp companyService ;
    @Autowired
    private JwtUtil jwtUtil ;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("here");
        final String authorizationHeader = request.getHeader("token");

        String name  = null ;
        String jwt = null;
        if (authorizationHeader!=null)
        {
            try {
                jwt = authorizationHeader;
                name = jwtUtil.extractEmail(jwt) ;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }

        }

        if(name != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            System.out.println(name+" tera dhyaan kidhar h");
            UserDetails userDetails = this.companyService.loadUserByUsername(name.toString()) ;
            if(jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()) ;
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        log.warn("here");

        filterChain.doFilter(request , response);
    }
}
