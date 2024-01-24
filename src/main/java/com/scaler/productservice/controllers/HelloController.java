package com.scaler.productservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/say/{variable}/{times}")
    public String sayHello(@PathVariable("variable") String name,
                           @PathVariable("times") int time) {
        String answer = "";
        for(int i = 0; i < time; i++) {
            answer += name;
            answer += "<br>";
        }
        return answer;
    }
}
