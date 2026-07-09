package mini.payment.errors;

import java.time.LocalDateTime;

public record ApiErrors(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

}
