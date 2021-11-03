package com.nullpointerexception.nullpointerexception.restapi.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String hello() {
        return "Hello my friend. Welcome in my project";
    }
}
