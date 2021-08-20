package com.learnreactivespring.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {
    Flux<String> stringFlux = Flux
        .just("Spring", "Spring Boot", "Reactive Spring")
        .concatWith(Flux.error(new RuntimeException(("Exception Occurred"))))
        .log();

    stringFlux
        .subscribe(System.out::println, (e) -> System.err.println(e));
  }
}
