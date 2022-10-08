package com.bank.webfluxpatterns.orchestratorsequence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.webfluxpatterns.orchestratorsequence.dto.OrchestrationRequestContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class OrderCancellationService {

    private Sinks.Many<OrchestrationRequestContext> sink;
    private Flux<OrchestrationRequestContext> flux;

    @Autowired
    private List<Orchestrator> orchestrators;

    @PostConstruct
    public void  init(){
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = this.sink.asFlux().publishOn(Schedulers.boundedElastic());
        orchestrators.forEach(o -> this.flux.subscribe(o.cancel()));
    }

    public void cancelOrder(OrchestrationRequestContext ctx){
        this.sink.tryEmitNext(ctx);
    }

}
