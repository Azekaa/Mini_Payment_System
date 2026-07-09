package mini.payment.dto;

import java.math.BigDecimal;


public record PaymentRequest(
        Long userId,
        BigDecimal amount
) {

}
