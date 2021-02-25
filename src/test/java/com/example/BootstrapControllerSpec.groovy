package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Specification
import spock.lang.Subject

import java.time.Duration

@WebFluxTest(controllers = BootstrapController, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration)
class BootstrapControllerSpec extends Specification {
    @Subject BootstrapController controller

    @Autowired WebTestClient webTestClient

    def setup(){
        controller = new BootstrapController()
    }

    def "POST to bootstrap emits an event to processor"(){
        when:
        webTestClient.post().uri("/bootstrap").body(BodyInserters.fromValue("test"))
                .exchange()

        then:
        controller.processor.blockFirst(Duration.ofSeconds(1)) == 1
    }

}
