package co.com.sofka.petproject.rentpayment.controllers;

import co.com.sofka.petproject.rentpayment.models.documents.ApartmentDocument;
import co.com.sofka.petproject.rentpayment.models.documents.PaymentDocument;
import co.com.sofka.petproject.rentpayment.models.dto.PaymentDTO;
import co.com.sofka.petproject.rentpayment.models.model.Payment;
import co.com.sofka.petproject.rentpayment.models.services.ApartmentService;
import co.com.sofka.petproject.rentpayment.models.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@AutoConfigureWebTestClient
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@WebFluxTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    PaymentService paymentService;
    @MockBean
    ApartmentService apartmentService;

    private PaymentDocument payment;

    @BeforeEach
    public void setup() {
        payment = PaymentDocument.builder()
                .id(UUID.randomUUID().toString())
                .tenantDocument("1214735811")
                .build();
    }

    @Test
    void savePayment() {
        Payment payment = Payment.builder()
                .id("123")
                .tenantDocument("1214735811")
                .apartmentId("code2")
                .paidValue(122.3)
                .payDate(new Date())
                .build();

        Payment mockPayment = mock(Payment.class);

        Mono<Payment> paymentMono = Mono.just(payment);

        Mockito.when(this.paymentService.save(Mockito.any(Payment.class))).thenReturn(paymentMono);
        Mockito.when(mockPayment.getTenantDocument()).thenReturn("1214735811");


        client.post()
                .uri("/api/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(payment), PaymentDocument.class)
                .exchange()
                .expectStatus()
                .isOk();

        assertEquals(payment.getTenantDocument(), mockPayment.getTenantDocument());

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

        ApartmentDocument apartment = new ApartmentDocument();
        apartment.setId("code1");
        apartment.setName("name1");
        apartment.setAddress("address1");

        Mono<Payment> paymentMono = Mono.just(payment);
        Mono<ApartmentDocument> apartmentMono = Mono.just(apartment);

        Mockito.when(paymentService.findById("12")).thenReturn(paymentMono);
        Mockito.when(apartmentService.findById("code1")).thenReturn(apartmentMono);

        client.get()
                .uri("/api/payments".concat("/{id}"), Collections.singletonMap("id", payment.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(PaymentDTO.class);

        Mockito.verify(paymentService, times(1)).findById("12");

    }

}