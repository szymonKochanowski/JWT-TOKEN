package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor //w springu zaleznosci mozemy wstrzyknac przez adnotacje autowired, seter lub konstruktor (zalecane), a przy uzyciu lomboka nawet w postaci adnotacji
public class HelloController {

    private final HelloService helloService;

//    @Autowired
//    private HelloService helloService;

//    @Autowired
//    public void setHelloService(HelloService helloService) {
//        this.helloService = helloService;
//    }

//    public HelloController(HelloService helloService) {
//        this.helloService = helloService;
//    }

    @GetMapping("/")
    public String hello() {
        return helloService.hello();
    }
}
