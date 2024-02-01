package com.ct_ecommerce.eshop.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.ct_ecommerce.eshop.AppUsers.AppUser;
import com.ct_ecommerce.eshop.AppUsers.AppUserRepository;
import com.ct_ecommerce.eshop.securityServices.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Filter for decoding a JWT in the Authorization header and loading the user
 * object into the authentication context.
 * @Component used so spring boot can see it and inject it automatically
 * OncePerRequestFilter abstract implementation of the request filter for spring framework
 */
@Component
public class JWTRequestsFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository appUserRepository;

    /**
     * Constructor for spring injection.
     * @param jwtService
     * @param appUserRepository ** for db access
     */
    public JWTRequestsFilter(JWTService jwtService, AppUserRepository appUserRepository){
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get token from header
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            //token exists and its valid..
            String token = tokenHeader.substring(7);
            try{
                String username = jwtService.getUsername(token);
                //check if user exists
                Optional<AppUser> opUser = appUserRepository.findUserByUsername(username);
                if(opUser.isPresent()){
                    //build auth object of user
                    AppUser user = opUser.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException ex){
                //
            }
        }

        filterChain.doFilter(request, response); //done.. go to next filter
    }
}
