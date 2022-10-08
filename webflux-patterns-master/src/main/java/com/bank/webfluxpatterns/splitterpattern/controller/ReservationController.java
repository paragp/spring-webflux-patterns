package com.bank.webfluxpatterns.splitterpattern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bank.webfluxpatterns.splitterpattern.dto.ReservationItemRequest;
import com.bank.webfluxpatterns.splitterpattern.dto.ReservationResponse;
import com.bank.webfluxpatterns.splitterpattern.service.ReservationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("splitterpattern")
public class ReservationController {

    @Autowired
    private ReservationService service;

    @PostMapping("reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> flux){
        return this.service.reserve(flux);
    }

}
