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

    @PostMapping("/purchase")
    private ResponseEntity<Seat> purchaseSeat(@RequestBody Seat seat) {
        if (seat.getRow() < 1 || seat.getRow() > model.getRows() || seat.getColumn() < 1 || seat.getColumn() > model.getColumns()) {
            throw new PurchaseTicketErrorException("The number of a row or a column is out of bounds!");
        }

        Seat result = model.purchaseSeat(seat);
        if (Objects.isNull(result)) {
            throw new PurchaseTicketErrorException("The ticket has been already purchased!");
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @ExceptionHandler(PurchaseTicketErrorException.class)
    ResponseEntity<Object> handleTicketException(PurchaseTicketErrorException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
