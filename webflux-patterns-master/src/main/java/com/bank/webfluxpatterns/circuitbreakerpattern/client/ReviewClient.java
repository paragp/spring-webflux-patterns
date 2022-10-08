package com.bank.webfluxpatterns.circuitbreakerpattern.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.webfluxpatterns.circuitbreakerpattern.dto.Review;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${circuitbreakerpattern.review.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // Circuit Breaker Pattern
    @CircuitBreaker(name = "review-service", fallbackMethod = "fallBackReview")
    public Mono<List<Review>> getReviews(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                .retry(5) // Retry Pattern
                .timeout(Duration.ofMillis(300)); // Timeout pattern with no fallback method
    }

    public Mono<List<Review>> fallBackReview(Integer id, Throwable ex) {
        System.out.println("fallback reviews called : " + ex.getMessage());
        return Mono.just(Collections.emptyList());
    }

}
