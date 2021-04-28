package co.com.sofka.petproject.rentpayment.controllers;

import co.com.sofka.petproject.rentpayment.models.model.Payment;
import co.com.sofka.petproject.rentpayment.models.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Mono<Payment> savePayment(@RequestBody Payment payment) {
        return paymentService.save(payment);
    }
}
