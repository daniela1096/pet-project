package co.com.sofka.petproject.rentpayment.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "apartmentDocument")
public class ApartmentDocument {

    @Id
    private String id;
    private String name;
    private String address;
}
