package com.learnreactivespring.fluxandmono;

import static reactor.core.scheduler.Schedulers.parallel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformTest {

  List<String> names = Arrays.asList("Adam", "Anna", "Jack", "Jenny");

  @Test
  public void transformUsingMap() {
    Flux<String> namesFlux = Flux.fromIterable(names)
        .map(s -> s.toUpperCase(Locale.ROOT))
        .log();

    StepVerifier.create(namesFlux)
        .expectNext("ADAM", "ANNA", "JACK", "JENNY")
        .verifyComplete();
  }

  @Test
  public void transformUsingMapLength() {
    Flux<Integer> namesFlux = Flux.fromIterable(names)
        .map(s -> s.length())
        .log();

    StepVerifier.create(namesFlux)
        .expectNext(4, 4, 4, 5)
        .verifyComplete();
  }

  @Test
  public void transformUsingMapLengthFilter() {
    Flux<String> namesFlux = Flux.fromIterable(names)
        .filter(s -> s.length() > 4)
        .map(s -> s.toUpperCase(Locale.ROOT))
        .log();

    StepVerifier.create(namesFlux)
        .expectNext("JENNY")
        .verifyComplete();
  }

  @Test
  public void transformUsingFlatMap() {
    Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
        .flatMap(s -> {
          return Flux.fromIterable(convertToList(s)); // simulate db call
        })
        .log(); // use case: db or external call per element that returns a flux

    StepVerifier.create(namesFlux)
        .expectNextCount(12)
        .verifyComplete();
  }

  private List<String> convertToList(String s) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Arrays.asList(s, " new Value");
  }

  @Test
  public void transformUsingFlatMapUsingParallel() {
    Flux<String> namesFlux = Flux.fromIterable(Arrays.asList("A", "B", "C", "D", "E", "F"))
        .window(2) // pass 2 elements at a time
//        concatMap does not maintain order
//        .concatMap()(s) ->
//            s.map(this::convertToList).subscribeOn(parallel())
        // maintains order
        .flatMapSequential((s) ->
              s.map(this::convertToList).subscribeOn(parallel())
              .flatMap(r -> Flux.fromIterable(r)))
        .log(); // use case: db or external call per element that returns a flux

    StepVerifier.create(namesFlux)
        .expectNextCount(12)
        .verifyComplete();
  }
}
