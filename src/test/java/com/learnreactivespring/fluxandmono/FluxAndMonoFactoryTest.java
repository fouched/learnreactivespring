package com.learnreactivespring.fluxandmono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

  List<String> names = Arrays.asList("Adam", "Anna", "Jack", "Jenny");

  @Test
  public void fluxUsingIterable() {
    Flux<String> namesFlux = Flux.fromIterable(names).log();

    StepVerifier.create(namesFlux)
        .expectNext("Adam", "Anna", "Jack", "Jenny")
        .verifyComplete();
  }

  @Test
  public void fluxUsingArray() {
    String[] names = new String[]{"Admin", "Anna", "Jack", "Jenny"};
    Flux<String> namesFlux = Flux.fromArray(names).log();

    StepVerifier.create(namesFlux)
        .expectNext("Admin", "Anna", "Jack", "Jenny")
        .verifyComplete();
  }

  @Test
  public void fluxUsingStream() {
    Flux<String> namesFlux = Flux.fromStream(names.stream()).log();

    StepVerifier.create(namesFlux)
        .expectNext("Admin", "Anna", "Jack", "Jenny")
        .verifyComplete();
  }

  @Test
  public void fluxUsingRange() {
    Flux<Integer> integerFlux = Flux.range(1, 5);

    StepVerifier.create(integerFlux)
        .expectNext(1, 2, 3, 4, 5)
        .verifyComplete();
  }

  @Test
  public void monoUsingJustOrEmpty() {
    Mono<String> mono = Mono.justOrEmpty(null);

    StepVerifier.create(mono.log())
        .verifyComplete();
  }

  @Test
  public void monoUsingSupplier() {

    Supplier<String> stringSupplier = () -> "Adam";

    Mono<String> mono = Mono.fromSupplier(stringSupplier);

    StepVerifier.create(mono.log())
        .expectNext("Adam")
        .verifyComplete();
  }
}
