package mini.payment.domain;

import mini.payment.domain.db.PaymentEntity;
import mini.payment.dto.PaymentDTO;
import mini.payment.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentEntityMapper {
    public PaymentDTO convertEntityToDto(PaymentEntity payment){
        return new PaymentDTO(
                payment.getId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdateAt());
    }
    public List<PaymentDTO> mapListToDto(List<PaymentEntity> payments) {
        return payments.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
