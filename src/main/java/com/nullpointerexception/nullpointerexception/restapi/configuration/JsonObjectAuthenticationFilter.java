package com.nullpointerexception.nullpointerexception.restapi.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonObjectAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader(); //zczytywanie requesta linia po lini do string buildera
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            LogiCredentials authRequest = objectMapper.readValue(sb.toString(), LogiCredentials.class); //z calej zawartosci requestu jest zamieniana na stringa, czyli mamy tutaj jsona i json przekazujemy do object mappera, json jest zamieniany na Login Credentials
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( //tworzymy userpassword authentitacion token
                    authRequest.getUsername(), authRequest.getPassword()
            );
            setDetails(request, token); //tokena przekazujemy do set details
            return this.getAuthenticationManager().authenticate(token);  //a nasetpnie do authentication managera
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
