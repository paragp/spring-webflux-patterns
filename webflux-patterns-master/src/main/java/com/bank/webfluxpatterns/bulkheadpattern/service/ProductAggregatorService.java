package com.bank.webfluxpatterns.bulkheadpattern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.webfluxpatterns.bulkheadpattern.client.ProductClient;
import com.bank.webfluxpatterns.bulkheadpattern.client.ReviewClient;
import com.bank.webfluxpatterns.bulkheadpattern.dto.Product;
import com.bank.webfluxpatterns.bulkheadpattern.dto.ProductAggregate;
import com.bank.webfluxpatterns.bulkheadpattern.dto.Review;

import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private ReviewClient reviewClient;

    public Mono<ProductAggregate> aggregate(Integer id){
        return Mono.zip(
               this.productClient.getProduct(id),
               this.reviewClient.getReviews(id)
        )
        .map(t -> toDto(t.getT1(), t.getT2()));
    }

    private ProductAggregate toDto(Product product, List<Review> reviews){
        return ProductAggregate.create(
                product.getId(),
                product.getCategory(),
                product.getDescription(),
                reviews
        );
    }


}
