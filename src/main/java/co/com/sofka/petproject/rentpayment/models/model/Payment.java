package co.com.sofka.petproject.rentpayment.models.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Payment {

    @Id
    private String id;
    private String tenantDocument;
    private Double paidValue;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;
    private String apartmentId;

}
