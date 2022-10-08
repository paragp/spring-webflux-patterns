package com.bank.webfluxpatterns.bulkheadpattern.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("bulkheadpattern")
public class FibController {

    // Schedulers.newParallel used for compute intensive blocking
    // Schedulers.boundedElastic used for IO intensive blocking
    // Limit 4 cpu cores for this API
    private final Scheduler scheduler = Schedulers.newParallel("fib", 4);

    // 0, 1, 1, 2, 3, 5, 8, 13....

    // CPU intensive
    @GetMapping("fib/{input}")
    // We can also use RateLimiter here in addition to reject the unnecessary
    // requests
    public Mono<ResponseEntity<Long>> fib(@PathVariable Long input) {
        return Mono.fromSupplier(() -> findFib(input))
                .subscribeOn(scheduler) // Event loop thread Use the schedulers to get things done such that it is not
                                        // stuck
                .map(ResponseEntity::ok);
        // timeout can also be set in case if this takes too much time
    }

    private Long findFib(Long input) {
        if (input < 2)
            return input;
        return findFib(input - 1) + findFib(input - 2);
    }

}
