package co.com.sofka.petproject.rentpayment.models.exception;

public class RentPaymentException extends RuntimeException{

    public RentPaymentException(RentPaymentErrorEnum error) {
        super(error.name());

    }
}
