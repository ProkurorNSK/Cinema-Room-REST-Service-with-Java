package cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
public class SeatsController {
    private final Model model;
    @Autowired
    private SeatsController(Model model) {
        this.model = model;
    }

    @GetMapping("/seats")
    private Model getSeats() {
        return model;
    }
}
