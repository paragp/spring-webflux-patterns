package com.bank.webfluxpatterns.splitterpattern.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.webfluxpatterns.splitterpattern.dto.RoomReservationRequest;
import com.bank.webfluxpatterns.splitterpattern.dto.RoomReservationResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RoomClient {

    private final WebClient client;

    public RoomClient(@Value("${splitterpattern.room.service}") String baseUrl){
        this.client = WebClient.builder()
                               .baseUrl(baseUrl)
                               .build();
    }

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> flux){
        return this.client
                .post()
                .body(flux, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }

}
