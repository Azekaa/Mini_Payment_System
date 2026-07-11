package mini.payment.Controller;

import jakarta.validation.Valid;
import mini.payment.domain.PaymentService;
import mini.payment.dto.PaymentDTO;
import mini.payment.dto.PaymentRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){ this.paymentService = paymentService;}

    @PostMapping
    public PaymentDTO create(@Valid @RequestBody PaymentRequest request){
        return paymentService.createPayment(request);
    }

    @GetMapping( {"/{id}"})
    public PaymentDTO get(@Valid @PathVariable Long id){
        return paymentService.getPayment(id);
    }

    @PostMapping("/{id}/confirm")
    public PaymentDTO confirm(@Valid @PathVariable Long id){
        return paymentService.confirmPayment(id);
    }

}
