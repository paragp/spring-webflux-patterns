package com.bank.webfluxpatterns.splitterpattern.service;

import com.bank.webfluxpatterns.splitterpattern.dto.ReservationItemRequest;
import com.bank.webfluxpatterns.splitterpattern.dto.ReservationItemResponse;
import com.bank.webfluxpatterns.splitterpattern.dto.ReservationType;

import reactor.core.publisher.Flux;

public abstract class ReservationHandler {

    protected abstract ReservationType getType();
    protected abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> flux);

}
