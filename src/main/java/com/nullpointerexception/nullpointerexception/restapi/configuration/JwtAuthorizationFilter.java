package com.nullpointerexception.nullpointerexception.restapi.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;
    private final String secret;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserDetailsService userDetailsService,
                                  String secret) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, //ta metoda odpowiada za autrozyacje
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request); // jezli klient strzela requestem do serwisu i ma tokena to go pobieramy
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication); // tutaj nastepuje autoryzacja uzytkownika
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER); // token header to authorization
        if (token != null && token.startsWith(TOKEN_PREFIX)) { //sprawdzamy czy ma odpowiedni
            String userName = JWT.require(Algorithm.HMAC256(secret)) // sprawdzamy czy odpowiedni secret
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, "")) // weryfikujemy token
                    .getSubject(); // z tokena mozemy pobrac rozne info
            if (userName != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName); // z tokena pobieramy userName i jezeli nie jest nulem to wrzucamy go do user datail service
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()); // przekazujemy usera do user password authenitication token
            }
        }
        return null;
    }
}
