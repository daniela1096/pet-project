package co.com.sofka.petproject.rentpayment.models.services;

import co.com.sofka.petproject.rentpayment.models.model.Payment;
import reactor.core.publisher.Mono;

public interface PaymentService {

    Mono<Payment> save(Payment payment);
}
