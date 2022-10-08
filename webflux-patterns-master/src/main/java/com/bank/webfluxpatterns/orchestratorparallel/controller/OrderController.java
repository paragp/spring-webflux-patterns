package com.bank.webfluxpatterns.orchestratorparallel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.webfluxpatterns.orchestratorparallel.dto.OrderRequest;
import com.bank.webfluxpatterns.orchestratorparallel.dto.OrderResponse;
import com.bank.webfluxpatterns.orchestratorparallel.service.OrchestratorService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orchestratorparallel")
public class OrderController {

    @Autowired
    private OrchestratorService service;

    @PostMapping("order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> mono) {
        return this.service.placeOrder(mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
