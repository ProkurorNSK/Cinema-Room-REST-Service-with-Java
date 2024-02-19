package cinema;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Model {
    private final Map<Seat, Boolean> seats;

    private final int rows;
    private final int columns;
    public Model() {
        rows = 9;
        columns = 9;
        seats = new ConcurrentHashMap<>();
        for (int j = 1; j <= rows; j++) {
            for (int i = 1; i <= columns; i++) {
                seats.put(new Seat(j, i), true);
            }
        }
    }

    Cinema getCinema() {
        List<Seat> freeSeats = new ArrayList<>();
        seats.forEach((seat, isFree) -> {
            if (isFree) freeSeats.add(seat);
        });
        Collections.sort(freeSeats);
        return new Cinema(rows, columns, freeSeats);
    }

    Seat purchaseSeat(Seat requestSeat) {
        for (Map.Entry<Seat, Boolean> entry : seats.entrySet()) {
            Seat seat = entry.getKey();
            Boolean isFree = entry.getValue();
            if (Objects.equals(seat, requestSeat) && isFree) {
                seats.put(seat, false);
                return seat;
            }
        }
        return null;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
