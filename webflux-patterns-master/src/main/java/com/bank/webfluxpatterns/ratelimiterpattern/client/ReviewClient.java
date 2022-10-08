package com.bank.webfluxpatterns.ratelimiterpattern.client;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.webfluxpatterns.ratelimiterpattern.dto.Review;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    // Define cache here preferably Caffeine Cache

    public ReviewClient(@Value("${ratelimiterpattern.review.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // Client side rate limiter to limit the no of request sent by client to the
    // server
    // To reduce the cost/respect of the contract
    @RateLimiter(name = "review-service", fallbackMethod = "fallback")
    public Mono<List<Review>> getReviews(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList();
        // .doOnNext(list -> //put the list in cache if required);

    }

    public Mono<List<Review>> fallback(Integer id, Throwable ex) {
        return Mono.just(Collections.emptyList());
        // return Mono.fromSupplier(()-> //read the list from the cache based on the
        // product id set in the getReviews method))
    }

}
