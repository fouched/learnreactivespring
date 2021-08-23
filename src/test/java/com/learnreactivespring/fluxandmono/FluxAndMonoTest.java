package com.learnreactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {
    Flux<String> stringFlux = Flux
        .just("Spring", "Spring Boot", "Reactive Spring")
//        .concatWith(Flux.error(new RuntimeException(("Exception Occurred"))))
        .concatWith(Flux.just("After error")) // does not display due to error - it stops sending data
        .log();

    stringFlux
        .subscribe(System.out::println, (e) -> System.err.println(">>>> Exception is: " + e),
            () -> System.out.println(">>>> Completed"));
    // 3rd argument above is optional, here a lamba is used to do something when flux completes.
    // It will not run if there is an error!
  }

  @Test
  public void monoTest() {
    Mono<String> stringMono = Mono.just("Spring");

    StepVerifier.create(stringMono.log())
        .expectNext("Spring")
        .verifyComplete();
  }

  @Test
  public void monoTestError() {
    Mono<String> stringMono = Mono.error(new RuntimeException("Exception occurred"));

    StepVerifier.create(stringMono)
        .expectError(RuntimeException.class)
        .verify();
  }
}
