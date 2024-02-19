package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PurchaseTicketErrorException extends RuntimeException{
    public PurchaseTicketErrorException(String message) {
        super(message);
    }
}
