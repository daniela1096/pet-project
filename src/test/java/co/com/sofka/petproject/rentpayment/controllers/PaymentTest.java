package co.com.sofka.petproject.rentpayment.controllers;

import co.com.sofka.petproject.rentpayment.models.model.Payment;
import co.com.sofka.petproject.rentpayment.models.services.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;

@RunWith(SpringRunner.class)
public class PaymentTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private Payment payment;


    @Test
    public void saveSuccessfulTest(){

        payment = Payment.builder()
                .id(UUID.randomUUID().toString())
                .tenantDocument("1214735811")
                .build();

        Mockito.when(paymentService.save(any())).thenReturn(Mono.just(payment));

        Mono<Payment> transactionMono = paymentController.savePayment(payment);

        assertThat(transactionMono.block()).isNotNull();
    }

    @Test
    public void ErrorToSaveTest(){

        payment = Payment.builder()
                .id(UUID.randomUUID().toString())
                .tenantDocument("")
                .build();

        Mockito.when(paymentService.save(any())).thenReturn(Mono.just(payment));

        Mono<Payment> transactionMono = paymentController.savePayment(payment);

        assertThat(transactionMono.materialize().block().getThrowable().getMessage()).contains("THE_FIELD_IS_REQUIRED");
    }

}
