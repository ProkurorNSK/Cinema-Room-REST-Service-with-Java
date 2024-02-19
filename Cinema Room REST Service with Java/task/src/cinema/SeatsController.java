package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
        }

        Seat result = model.purchaseSeat(seat);
        if (Objects.isNull(result)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ticket has been already purchased!");
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
