package com.bank.webfluxpatterns.orchestratorsequence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.webfluxpatterns.orchestratorsequence.dto.OrchestrationRequestContext;
import com.bank.webfluxpatterns.orchestratorsequence.dto.OrderRequest;
import com.bank.webfluxpatterns.orchestratorsequence.dto.OrderResponse;
import com.bank.webfluxpatterns.orchestratorsequence.dto.Status;
import com.bank.webfluxpatterns.orchestratorsequence.util.DebugUtil;

import reactor.core.publisher.Mono;

@Service
public class OrchestratorService {

    @Autowired
    private OrderFulfillmentService fulfillmentService;

    @Autowired
    private OrderCancellationService cancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> mono){
        return mono
                .map(OrchestrationRequestContext::new)
                .flatMap(fulfillmentService::placeOrder)
                .doOnNext(this::doOrderPostProcessing)
                .doOnNext(DebugUtil::print) // just for debugging
                .map(this::toOrderResponse);
    }

    private void doOrderPostProcessing(OrchestrationRequestContext ctx){
        if(Status.FAILED.equals(ctx.getStatus()))
            this.cancellationService.cancelOrder(ctx);
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext ctx){
        var isSuccess = Status.SUCCESS.equals(ctx.getStatus());
        var address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
        var deliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDelivery() : null;
        return OrderResponse.create(
            ctx.getOrderRequest().getUserId(),
            ctx.getOrderRequest().getProductId(),
            ctx.getOrderId(),
            ctx.getStatus(),
            address,
            deliveryDate
        );
    }

}
