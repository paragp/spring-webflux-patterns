package com.bank.webfluxpatterns;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.webfluxpatterns.bulkheadpattern.dto.ProductAggregate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BulkheadTest {

    private WebClient client;

    @BeforeAll
    public void setClient() {
        this.client = WebClient.builder()
                .baseUrl("http://localhost:8080/bulkheadpattern/")
                .build();
    }

    // Calling fibRequests & productRequests in Parallel
    // Flux.merge will subscribe all the publishers at the same time
    @Test
    public void concurrentUsersTest() {
        StepVerifier.create(Flux.merge(fibRequests(), productRequests()))
                .verifyComplete();
    }

    // Simulating the parallel request
    // Test can be run for the range 1,2 to understand the flow
    // Further it can be enhanced basis the no of cores in the machine
    private Mono<Void> fibRequests() {
        return Flux.range(1, 46)
                // Flatmap will send the 40 request in parallel
                .flatMap(i -> this.client.get().uri("fib/46").retrieve().bodyToMono(Long.class))
                .doOnNext(this::print)
                .then();
    }

    // Parallel product request
    // Test can be run for the range 1,2 to understand the flow
    // Further it can be enhanced basis the no of cores in the machine
    private Mono<Void> productRequests() {
        return Mono.delay(Duration.ofMillis(100)) // Delay the execution by 100 ms
                .thenMany(Flux.range(1, 46))
                .flatMap(i -> this.client.get().uri("product/1").retrieve().bodyToMono(ProductAggregate.class))
                .map(ProductAggregate::getCategory)
                .doOnNext(this::print)
                .then();
    }

    private void print(Object o) {
        System.out.println(LocalDateTime.now() + " : " + o);
    }

}
