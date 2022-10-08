package com.bank.webfluxpatterns.circuitbreakerpattern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.webfluxpatterns.circuitbreakerpattern.dto.ProductAggregate;
import com.bank.webfluxpatterns.circuitbreakerpattern.service.ProductAggregatorService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("circuitbreakerpattern")
public class ProductAggregateController {

    @Autowired
    private ProductAggregatorService service;

    @GetMapping("product/{id}")
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable Integer id) {
        return this.service.aggregate(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
