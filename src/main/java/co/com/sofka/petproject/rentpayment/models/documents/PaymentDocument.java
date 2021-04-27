package co.com.sofka.petproject.rentpayment.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection="paymentsDocument")
public class PaymentDocument {

    @Id
    private String id;
    private String tenantDocument;
    private Double paidValue;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;
    private String apartmentId;

}