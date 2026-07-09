package mini.payment.domain;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mini.payment.dto.PaymentDTO;
import mini.payment.dto.PaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (request.amount().compareTo(MAX_AMOUNT) > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Amount too large");
        }
        PaymentEntity saved = paymentRepository.save(payment);
        log.info("Payment created id={}", saved.getId());
        return convertEntityToDto(saved);
    }

    public PaymentDTO getPayment(Long id) {
        PaymentEntity payment = findPaymentOrThrow(id);
        return convertEntityToDto(payment);
    }

    @Transactional
    public PaymentDTO confirmPayment(Long id) throws RuntimeException {
        PaymentEntity payment = findPaymentOrThrow(id);
        if (payment.getStatus() != PaymentStatus.NEW){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment status must be NEW");
        }
        payment.setStatus(PaymentStatus.SUCCEEDED);
        PaymentEntity saved = paymentRepository.save(payment);
        log.info("Payment has been confirmed: id={}",id);
        return convertEntityToDto(saved);
    }

    private PaymentEntity findPaymentOrThrow(Long id){
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Payment with id=" + id + "not found"));
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
