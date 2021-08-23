package com.learnreactivespring.fluxandmono;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoFilterTest {

  List<String> names = Arrays.asList("Adam", "Anna", "Jack", "Jenny");

  @Test
  public void filterTest() {
    Flux<String> nameFlux = Flux.fromIterable(names)
        .filter(s -> s.startsWith("A"))
        .log();

    StepVerifier.create(nameFlux)
        .expectNext("Adam", "Anna")
        .verifyComplete();
  }

  @Test
  public void filterTestLength() {
    Flux<String> nameFlux = Flux.fromIterable(names)
        .filter(s -> s.length() > 4)
        .log();

    StepVerifier.create(nameFlux)
        .expectNext("Jenny")
        .verifyComplete();
  }
}
