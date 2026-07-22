package mini.payment.domain;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import mini.payment.domain.db.PaymentEntity;
import mini.payment.domain.db.PaymentRepository;
import mini.payment.domain.db.UserRepository;
import mini.payment.dto.PaymentDTO;
import mini.payment.dto.PaymentRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Slf4j
@Service
public class PaymentService {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("10000.00");

    private final PaymentRepository paymentRepository;
    private final PaymentEntityMapper mapper;
    private final UserRepository userRepository;
    private final RedisTemplate<String,PaymentDTO> redisTemplate;

    public PaymentService(PaymentRepository paymentRepository, PaymentEntityMapper mapper, UserRepository userRepository, RedisTemplate redisTemplate) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public PaymentDTO createPayment(PaymentRequest request) {
        if(!userRepository.existsById(request.userId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found id = " + request.userId());
        }
        if (request.amount().compareTo(MAX_AMOUNT) > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Amount too large");
        }
        PaymentEntity payment = new PaymentEntity(request.userId(), request.amount(), PaymentStatus.NEW);
        PaymentEntity saved = paymentRepository.save(payment);
        log.info("Payment created id={}", saved.getId());
        return mapper.convertEntityToDto(saved);
    }

//    @Cacheable(value = "payments",key = "#id")
//    public PaymentDTO getPayment(Long id){
//        PaymentEntity payment = findPaymentOrThrow(id);
//        return mapper.convertEntityToDto(payment);
//    }
    public PaymentDTO getPayment(Long id) {
        var foundCache = redisTemplate.opsForValue().get(id.toString());
        if(foundCache != null){
            log.info("Payment found in cache: id={}",foundCache.id());
            return foundCache;
        }
        PaymentEntity payment = findPaymentOrThrow(id);
        var paymentDto = mapper.convertEntityToDto(payment);
        redisTemplate.opsForValue().set(id.toString(),paymentDto);
        return paymentDto;
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
        redisTemplate.delete(id.toString());
        return mapper.convertEntityToDto(saved);

    }

    @Transactional
    public PaymentDTO rejectPayment(Long id) throws RuntimeException{
        PaymentEntity payment = findPaymentOrThrow(id);
        if(payment.getStatus() != PaymentStatus.NEW){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment status must be NEW");
        }
        payment.setStatus(PaymentStatus.FAILED);
        PaymentEntity saved = paymentRepository.save(payment);
        log.info("change the status to FAILED: id={}",id);
        return mapper.convertEntityToDto(saved);
    }

    private PaymentEntity findPaymentOrThrow(Long id){
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Payment with id=" + id + "not found"));
    }
}