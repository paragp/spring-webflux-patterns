package com.bank.webfluxpatterns.orchestratorparallel.service;

import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.bank.webfluxpatterns.orchestratorparallel.dto.OrchestrationRequestContext;

public abstract class Orchestrator {

    public abstract Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx);
    public abstract Predicate<OrchestrationRequestContext> isSuccess();
    public abstract Consumer<OrchestrationRequestContext> cancel();

}
