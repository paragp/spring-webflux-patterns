package com.bank.webfluxpatterns.retrypattern.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bank.webfluxpatterns.retrypattern.dto.Review;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${retrypattern.review.service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<Review>> getReviews(Integer id) {
        return this.client
                .get()
                .uri("{id}", id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())// Doesnot Retry for 4xx errors
                .bodyToFlux(Review.class)
                .collectList()
                .retry(5)
                .timeout(Duration.ofMillis(300))// Maximum limit to retry is 300 ms to avoid increasing of overall
                                                // response time
                .onErrorReturn(Collections.emptyList());
    }

}
