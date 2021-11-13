package com.nullpointerexception.nullpointerexception.restapi.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.sql.DataSource;

@Configuration //jest to wymagana adnotacja
public class SecurityConfig extends WebSecurityConfigurerAdapter { //musi roszerzac ta klase adapter

    private final DataSource datasource;
    private final ObjectMapper objectMapper;
    private final RestAuthenticationSuccessHandler successHandler;
    private final RestAuthenticationFailureHandler failureHandler;
    private final String secret;

    public SecurityConfig(DataSource datasource, ObjectMapper objectMapper, RestAuthenticationSuccessHandler successHandler, RestAuthenticationFailureHandler failureHandler,
                          @Value("${jwt.secret}") String secret) {
        this.datasource = datasource;
        this.objectMapper = objectMapper;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.secret = secret;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .withDefaultSchema() //dodajemy to jezli nie mamy zrobionej tabeli user
                .dataSource(datasource)
                .withUser("test")
                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("test"))
                .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/h2-console/**").permitAll() //dostep do bazy h2
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ustawiamy aby sesja byla bez stanowa
            .and()
            .addFilter(authenticationFilter())
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsManager(), secret)) //musielismy ustawic wlasny user detail service (manager)
            .exceptionHandling()
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)) //ustawiany odpowiedni status http - 401 brak autoryzacji
            .and()
            .headers().frameOptions().disable(); //to dodajemy aby polaczyc sie z baza daych h2 i zobaczyc tabele z userem poprzez wylaczaenie domyslnych headersow od springa
    }
    
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter jsonObjectAuthenticationFilter = new JsonObjectAuthenticationFilter(objectMapper);
        jsonObjectAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);  //jest wywolywany gdy uwierzytelenienie jest poprawne
        jsonObjectAuthenticationFilter.setAuthenticationFailureHandler(failureHandler); //jest wywolywany gdy uwierzytelenienie jest niepoprawne
        jsonObjectAuthenticationFilter.setAuthenticationManager(super.authenticationManager()); //musimy tez ustawic manager z domyslna wartoscia
        return  jsonObjectAuthenticationFilter;
    }

    @Bean
    public UserDetailsManager userDetailsManager() { //konfigurejmy user datails service
        return new JdbcUserDetailsManager(datasource);
    }
}
