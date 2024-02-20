package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
@RestController
public class SeatsController {
    private final Model model;

    @Autowired
    private SeatsController(Model model) {
        this.model = model;
    }

    @GetMapping("/seats")
    private Cinema getSeats() {
        return model.getCinema();
    }

    @GetMapping("/stats")
    private ResponseEntity<Object> getStats(@RequestParam(required = false) String password ) {
        if (!Objects.equals(password, "super_secret")) {
            throw new PurchaseTicketErrorException("The password is wrong!", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(model.getStats());
    }

    @PostMapping("/purchase")
    private ResponseEntity<Ticket> purchaseSeat(@RequestBody Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > model.getRows() || seat.getColumn() < 1 || seat.getColumn() > model.getColumns()) {
            throw new PurchaseTicketErrorException("The number of a row or a column is out of bounds!", HttpStatus.BAD_REQUEST);
        }

        Ticket result = model.purchaseSeat(seat);
        if (Objects.isNull(result)) {
            throw new PurchaseTicketErrorException("The ticket has been already purchased!", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/return")
    private ResponseEntity<Object> returnSeat(@RequestBody Ticket token) {
        Seat result = model.returnSeat(token.token());
        if (Objects.isNull(result)) {
            throw new PurchaseTicketErrorException("Wrong token!", HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(Map.of("ticket", result));
        }
    }

    @ExceptionHandler(PurchaseTicketErrorException.class)
    ResponseEntity<Object> handleTicketException(PurchaseTicketErrorException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), ex.getHttpStatus());
    }


}
