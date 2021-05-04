package co.com.sofka.petproject.rentpayment.models.dao;

import co.com.sofka.petproject.rentpayment.models.documents.ApartmentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ApartmentRepository extends ReactiveMongoRepository<ApartmentDocument, String> {
}
