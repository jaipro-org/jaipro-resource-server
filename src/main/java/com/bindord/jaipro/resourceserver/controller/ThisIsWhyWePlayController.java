package com.bindord.jaipro.resourceserver.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("${service.ingress.context-path}/this-is-why-we-play")
public class ThisIsWhyWePlayController {

    @GetMapping(value = "",
            produces = {MediaType.TEXT_PLAIN_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<String> letsDoThis() {
        return Flux.just("Sky");
    }

}
