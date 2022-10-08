package com.bank.webfluxpatterns.timeoutpattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.webfluxpatterns.timeoutpattern.client.ProductClient;
import com.bank.webfluxpatterns.timeoutpattern.client.ReviewClient;
import com.bank.webfluxpatterns.timeoutpattern.dto.*;

import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id) {
        return Mono.zip(
                this.productClient.getProduct(id),
                this.reviewClient.getReviews(id))
                .map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toDto(Product product, List<Review> reviews) {
        return ProductAggregate.create(
                product.getId(),
                product.getCategory(),
                product.getDescription(),
                reviews);
    }

}
