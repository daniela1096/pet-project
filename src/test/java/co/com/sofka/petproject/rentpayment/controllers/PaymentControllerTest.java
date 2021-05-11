package co.com.sofka.petproject.rentpayment.controllers;

import co.com.sofka.petproject.rentpayment.models.documents.PaymentDocument;
import co.com.sofka.petproject.rentpayment.models.model.Payment;
import co.com.sofka.petproject.rentpayment.models.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    PaymentService paymentService;

    private PaymentDocument payment;

    @BeforeEach
    public void setup(){
        payment = PaymentDocument.builder()
                .id(UUID.randomUUID().toString())
                .tenantDocument("1214735811")
                .build();
    }

    @Test
    void savePayment() {
        Payment payment = Payment.builder()
                .id("1")
                .tenantDocument("98765")
                .apartmentId("code2")
                .paidValue(122.3)
                .payDate(new Date())
                .build();

        Mono<Payment> paymentMono = Mono.just(payment);

        Mockito.when(paymentService.save(payment)).thenReturn(paymentMono);

        client.post()
                .uri("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(payment), PaymentDocument.class)
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(paymentService, times(1)).save(refEq(payment));
    }

    @Test
    void findAll() {

        Payment payment = new Payment();
        payment.setId("12");
        payment.setTenantDocument("123456789");
        payment.setPaidValue(123.0);
        payment.setApartmentId("code1");

        List<Payment> list = new ArrayList<Payment>();
        list.add(payment);

        Flux<Payment> paymentFlux = Flux.fromIterable(list);

        Mockito.when(paymentService.findAll()).thenReturn(paymentFlux);

        client.get()
                .uri("/api/payments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(PaymentDocument.class);

        Mockito.verify(paymentService, times(1)).findAll();
    }

    @Test
    void findById() {

        Payment payment = new Payment();
        payment.setId("12");
        payment.setTenantDocument("123456789");
        payment.setPaidValue(123.0);
        payment.setApartmentId("code1");

        Mono<Payment> paymentMono = Mono.just(payment);

        Mockito.when(paymentService.findById("12")).thenReturn(paymentMono);

        client.get()
                .uri("/api/payments".concat("/{id}"), Collections.singletonMap("id", payment.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Payment.class);

        Mockito.verify(paymentService, times(1)).findById("12");

    }


}