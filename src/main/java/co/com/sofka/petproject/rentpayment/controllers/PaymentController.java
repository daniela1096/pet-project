package co.com.sofka.petproject.rentpayment.controllers;

import co.com.sofka.petproject.rentpayment.models.dto.PaymentDTO;
import co.com.sofka.petproject.rentpayment.models.exception.RentPaymentErrorEnum;
import co.com.sofka.petproject.rentpayment.models.exception.RentPaymentException;
import co.com.sofka.petproject.rentpayment.models.model.Payment;
import co.com.sofka.petproject.rentpayment.models.services.ApartmentService;
import co.com.sofka.petproject.rentpayment.models.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;
    @Autowired
    private final ApartmentService apartmentService;

    @Autowired
    public PaymentController(PaymentService paymentService, ApartmentService apartmentService) {
        this.paymentService = paymentService;
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public Mono<Payment> savePayment(@RequestBody Payment payment) {
        log.info("PAYMENT: {}", payment.getTenantDocument());
        return Mono.just(payment)
                .filter(paymentModel -> StringUtils.isNotEmpty(paymentModel.getTenantDocument()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RentPaymentException(RentPaymentErrorEnum.THE_FIELD_IS_REQUIRED))))
                .flatMap(paymentService::save);
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Payment>>> findAll() {
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PaymentDTO>> findById(@PathVariable String id) {
        return paymentService.findById(id)
                .map(payment -> PaymentDTO.builder()
                        .id(payment.getId())
                        .tenantDocument(payment.getTenantDocument())
                        .paidValue(payment.getPaidValue())
                        .payDate(payment.getPayDate())
                        .apartmentId(payment.getApartmentId())
                        .build())
                .flatMap(paymentDTO -> apartmentService.findById(paymentDTO.getApartmentId())
                        .map(apartment -> paymentDTO.toBuilder()
                                .apartmentName(apartment.getName())
                                .apartmentAddress(apartment.getAddress())
                                .build()))
                .map(payment -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(payment))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
