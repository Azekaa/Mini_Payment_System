package mini.payment;

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
    public PaymentDTO create(@RequestBody PaymentRequest request){
        return paymentService.createPayment(request);
    }

    @GetMapping({"/{id}"})
    public PaymentDTO get(@PathVariable Long id){
        return paymentService.getPayment(id);
    }

    @PostMapping("/{id}/confirm")
    public PaymentDTO confirm(@PathVariable Long id){
        return paymentService.confirmPayment(id);
    }

}
