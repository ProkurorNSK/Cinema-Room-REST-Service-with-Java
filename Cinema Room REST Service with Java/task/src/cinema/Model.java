package cinema;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Model {
    private final int rows;
    private final int columns;
    private final List<Seat> seats;

    public Model() {
        rows = 9;
        columns = 9;
        seats = new ArrayList<>();
        for (int j = 1; j <= rows; j++) {
            for (int i = 1; i <= columns; i++) {
                seats.add(new Seat(j, i));
            }
        }
    }

    @SuppressWarnings("unused")
    public int getRows() {
        return rows;
    }

    @SuppressWarnings("unused")
    public int getColumns() {
        return columns;
    }

    @SuppressWarnings("unused")
    public List<Seat> getSeats() {
        return seats;
    }
}
