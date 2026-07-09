package mini.payment.dto;

import mini.payment.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record PaymentDTO(
        Long id,
        Long userId,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updateAt
){


}
