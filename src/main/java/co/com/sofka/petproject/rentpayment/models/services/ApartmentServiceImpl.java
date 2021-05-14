package co.com.sofka.petproject.rentpayment.models.services;

import co.com.sofka.petproject.rentpayment.models.dao.ApartmentRepository;
import co.com.sofka.petproject.rentpayment.models.documents.ApartmentDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Override
    public Mono<ApartmentDocument> findById(String id) {
        return apartmentRepository.findById(id);
    }
}
