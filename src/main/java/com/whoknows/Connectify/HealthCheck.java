package com.whoknows.Connectify;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/test")
    public void test(){
        System.out.println("Hii ");
    }

}
