package mini.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;


public record PaymentRequest(
       @NotNull Long userId,
       @NotNull @Positive BigDecimal amount
) {

}
