package com.bank.webfluxpatterns.orchestratorsequence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ShippingResponse {

    private UUID shippingId;
    private Integer quantity;
    private Status status;
    private String expectedDelivery;
    private Address address;

}