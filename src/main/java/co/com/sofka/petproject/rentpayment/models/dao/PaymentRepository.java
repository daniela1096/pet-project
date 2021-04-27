package co.com.sofka.petproject.rentpayment.models.dao;

import co.com.sofka.petproject.rentpayment.models.documents.PaymentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentRepository extends ReactiveMongoRepository<PaymentDocument, String> {
}
