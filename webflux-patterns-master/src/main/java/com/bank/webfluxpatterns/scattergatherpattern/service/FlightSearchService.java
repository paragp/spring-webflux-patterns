package com.bank.webfluxpatterns.scattergatherpattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.webfluxpatterns.scattergatherpattern.client.DeltaClient;
import com.bank.webfluxpatterns.scattergatherpattern.client.FrontierClient;
import com.bank.webfluxpatterns.scattergatherpattern.client.JetBlueClient;
import com.bank.webfluxpatterns.scattergatherpattern.dto.FlightResult;

import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class FlightSearchService {

    @Autowired
    private DeltaClient deltaClient;

    @Autowired
    private FrontierClient frontierClient;

    @Autowired
    private JetBlueClient jetBlueClient;

    public Flux<FlightResult> getFlights(String from, String to) {
        return Flux.merge(
                this.deltaClient.getFlights(from, to),
                this.frontierClient.getFlights(from, to),
                this.jetBlueClient.getFlights(from, to))
                .take(Duration.ofSeconds(3));
    }

}
