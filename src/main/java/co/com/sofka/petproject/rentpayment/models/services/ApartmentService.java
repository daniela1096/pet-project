package co.com.sofka.petproject.rentpayment.models.services;

import co.com.sofka.petproject.rentpayment.models.documents.ApartmentDocument;
import reactor.core.publisher.Mono;

public interface ApartmentService {

    Mono<ApartmentDocument> findById(String id);

}
