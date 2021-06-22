package co.com.sofka.petproject.rentpayment.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PaymentDTO {

    @Id
    private String id;
    private String tenantDocument;
    private Double paidValue;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;
    private String apartmentId;
    private String apartmentName;
    private String apartmentAddress;

}
