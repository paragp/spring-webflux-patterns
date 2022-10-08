# Design Patterns with Spring WebFlux

This repo contains the source code for all the sections discussed in this course.

![design patterns with webflux](doc/course-image.png)

Spring WebFlux is a reactive non-blocking web stack which scales better & provides better performance compared to traditional Spring Web MVC! In this course, we learn various integration and resilient design patterns with Spring WebFlux to build Reactive Microservices.

By the end of this course you would be comfortable with:

## Integration Patterns:

Handling multiple reactive microservices communication in a much better way!

- Gateway Aggregator Pattern
- Scatter Gather Pattern
- Orchestrator Pattern (SAGA - for parallel workflow)
- Orchestrator Pattern (for sequential workflow)
- Splitter Pattern

## Resilient Patterns:

How to create more robust & resilient reactive microservices.

- Timeout Pattern --reactor
- Retry Pattern -- reactor
- Circuite Breaker Pattern -- resillience4j
- Rate Limiter Pattern -- resillience4j (no of calls in a specified window. Reject other calls)
- Bulkhead Pattern -- reactor (no of concurrent calls. Queue other calls)

In case of IO intensive application you don't have to do anything special, reactive libraries are good enough
In case of compute intensive things it better to have one dedicated threadpool and use that to do things

Refer How to Run.txt file to run the application
