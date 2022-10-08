package com.bank.webfluxpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Enable as per the pattern you wish to execute
//@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.bulkheadpattern")
//@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.circuitbreakerpattern")
//@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.orchestratorparallel")
//@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.orchestratorsequence")
//@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.ratelimiterpattern")
//@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.retrypattern")
@SpringBootApplication(scanBasePackages = "com.bank.webfluxpatterns.scattergatherpattern")
// @SpringBootApplication(scanBasePackages =
// "com.bank.webfluxpatterns.splitterpattern")
// @SpringBootApplication(scanBasePackages =
// "com.bank.webfluxpatterns.timeoutpattern")
// @SpringBootApplication(scanBasePackages =
// "com.bank.webfluxpatterns.aggregatorpattern")
public class WebfluxPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPatternsApplication.class, args);
	}

}
