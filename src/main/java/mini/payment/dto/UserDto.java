package mini.payment.dto;

import java.util.List;

public record UserDto (
    Long id,
    String email,
    List<PaymentDTO> payment
){}

