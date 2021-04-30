package co.com.sofka.petproject.rentpayment.models.services;

import co.com.sofka.petproject.rentpayment.models.dao.PaymentRepository;
import co.com.sofka.petproject.rentpayment.models.documents.PaymentDocument;
import co.com.sofka.petproject.rentpayment.models.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Mono<Payment> save(Payment payment) {
        return Mono.just(payment)
                .map(paymentDocument -> PaymentDocument.builder()
                        .id(paymentDocument.getId())
                        .tenantDocument(paymentDocument.getTenantDocument())
                        .paidValue(paymentDocument.getPaidValue())
                        .payDate(paymentDocument.getPayDate())
                        .apartmentId(paymentDocument.getApartmentId())
                        .build())
                .flatMap(paymentRepository::save)
                .thenReturn(payment);
    }

    @Override
    public Flux<Payment> findAll() {
        return paymentRepository.findAll()
                .map(paymentDocument -> Payment.builder()
                        .id(paymentDocument.getId())
                        .tenantDocument(paymentDocument.getTenantDocument())
                        .paidValue(paymentDocument.getPaidValue())
                        .payDate(paymentDocument.getPayDate())
                        .apartmentId(paymentDocument.getApartmentId())
                        .build());
    }

    @Override
    public Mono<Payment> findById(String id) {
        return paymentRepository.findById(id)
                .map(paymentDocument -> Payment.builder()
                        .id(paymentDocument.getId())
                        .tenantDocument(paymentDocument.getTenantDocument())
                        .paidValue(paymentDocument.getPaidValue())
                        .payDate(paymentDocument.getPayDate())
                        .apartmentId(paymentDocument.getApartmentId())
                        .build());
    }
}
