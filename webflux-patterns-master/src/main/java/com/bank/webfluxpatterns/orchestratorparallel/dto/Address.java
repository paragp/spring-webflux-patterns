package com.bank.webfluxpatterns.orchestratorparallel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Address {

    private String street;
    private String city;
    private String state;
    private String zipCode;

}
