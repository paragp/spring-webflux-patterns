package com.bank.webfluxpatterns.orchestratorsequence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.webfluxpatterns.orchestratorsequence.client.ProductClient;
import com.bank.webfluxpatterns.orchestratorsequence.dto.OrchestrationRequestContext;
import com.bank.webfluxpatterns.orchestratorsequence.dto.Product;
import com.bank.webfluxpatterns.orchestratorsequence.dto.Status;
import com.bank.webfluxpatterns.orchestratorsequence.util.OrchestrationUtil;

import reactor.core.publisher.Mono;

@Service
public class OrderFulfillmentService {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PaymentOrchestrator paymentOrchestrator;

    @Autowired
    private InventoryOrchestrator inventoryOrchestrator;

    @Autowired
    private ShippingOrchestrator shippingOrchestrator;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx){
        return this.getProduct(ctx)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(this.paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(this.inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(this.shippingOrchestrator::create)
                .doOnNext(c -> c.setStatus(Status.SUCCESS))
                .doOnError(ex -> ctx.setStatus(Status.FAILED))
                .onErrorReturn(ctx);
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx){
        return this.productClient.getProduct(ctx.getOrderRequest().getProductId())
                                 .map(Product::getPrice)
                                 .doOnNext(ctx::setProductPrice)
                                 .map(i -> ctx);
    }

}
