package com.bank.webfluxpatterns.orchestratorsequence.util;

import com.bank.webfluxpatterns.orchestratorsequence.dto.InventoryRequest;
import com.bank.webfluxpatterns.orchestratorsequence.dto.OrchestrationRequestContext;
import com.bank.webfluxpatterns.orchestratorsequence.dto.PaymentRequest;
import com.bank.webfluxpatterns.orchestratorsequence.dto.ShippingRequest;

public class OrchestrationUtil {

    public static void buildPaymentRequest(OrchestrationRequestContext ctx){
        var paymentRequest = PaymentRequest.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    public static void buildInventoryRequest(OrchestrationRequestContext ctx){
        var inventoryRequest = InventoryRequest.create(
            ctx.getPaymentResponse().getPaymentId(),
            ctx.getOrderRequest().getProductId(),
            ctx.getOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    public static void buildShippingRequest(OrchestrationRequestContext ctx){
        var shippingRequest = ShippingRequest.create(
                ctx.getOrderRequest().getQuantity(),
                ctx.getOrderRequest().getUserId(),
                ctx.getInventoryResponse().getInventoryId()
        );
        ctx.setShippingRequest(shippingRequest);
    }

}
