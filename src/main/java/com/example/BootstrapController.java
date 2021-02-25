package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Controller
public class BootstrapController {
    final EmitterProcessor<String> processor = EmitterProcessor.create();

    @RequestMapping("/bootstrap")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delegateToSupplier(@RequestBody String body) {
        processor.onNext(body);
    }

    @Bean
    public Supplier<Flux<Integer>> bootstrap(){
        return () -> processor.flatMap( trigger -> Flux.just(1, 2, 3));
    }

}
