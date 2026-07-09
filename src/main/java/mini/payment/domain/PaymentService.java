package mini.payment.domain;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mini.payment.dto.PaymentDTO;
import mini.payment.dto.PaymentRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class PaymentService {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("10000.00");

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentDTO createPayment(PaymentRequest request) {
        PaymentEntity payment = new PaymentEntity(request.userId(), request.amount(), PaymentStatus.NEW);
        PaymentEntity saved = paymentRepository.save(payment);
        log.info("Payment created id={}", saved.getId());
        return convertEntityToDto(saved);
    }

    public PaymentDTO getPayment(Long id) {
        PaymentEntity payment = findPaymentOrThrow(id);
        return convertEntityToDto(payment);
    }

    @Transactional
    public PaymentDTO confirmPayment(Long id) {
        PaymentEntity payment = findPaymentOrThrow(id);
        payment.setStatus(PaymentStatus.SUCCEEDED);
        PaymentEntity saved = paymentRepository.save(payment);
        log.info("Payment has been confirmed: id={}",id);
        return convertEntityToDto(saved);
    }

    private PaymentEntity findPaymentOrThrow(Long id){
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment with id=" + id + " not founded"));
    }

    private PaymentDTO convertEntityToDto(PaymentEntity payment){
        return new PaymentDTO(
                payment.getId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdateAt()
        );
    }


}
