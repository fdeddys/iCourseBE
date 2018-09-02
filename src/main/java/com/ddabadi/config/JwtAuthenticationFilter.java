package com.ddabadi.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(BaseConstant.HEADER_STRING);
        String username = null;
        String authToken = null;

        if (header != null &&  header.startsWith(BaseConstant.TOKEN_PREFIX)){
            authToken = header.replace(BaseConstant.TOKEN_PREFIX,"");

            try {
                username = jwtUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Error get username dari token");
            } catch (ExpiredJwtException e) {
                logger.warn("Token expired ");
            } catch(SignatureException e){
                logger.error("Authentication Failed.");
            }
        }

        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if( jwtUtil.validateToken(authToken, userDetails)){
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(
                                new SimpleGrantedAuthority("ROLE_ADMIN")
                        ));

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);

    }

}
