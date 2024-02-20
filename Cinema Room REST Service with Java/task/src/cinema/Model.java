package cinema;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Model {
    private final Map<Seat, Boolean> seats;
    private final Map<String, Seat> tokens;

    private final int rows;
    private final int columns;
    public Model() {
        rows = 9;
        columns = 9;
        seats = new ConcurrentHashMap<>();
        tokens = new ConcurrentHashMap<>();
        for (int j = 1; j <= rows; j++) {
            for (int i = 1; i <= columns; i++) {
                seats.put(new Seat(j, i), true);
            }
        }
    }

    public Map<String, Integer> getStats() {
        int purchased = tokens.size();
        int available = rows * columns - purchased;
        int income = tokens.values().stream().mapToInt(Seat::getPrice).sum();

        return Map.of("income", income, "available", available, "purchased", purchased);
    }

    Cinema getCinema() {
        List<Seat> freeSeats = new ArrayList<>();
        seats.forEach((seat, isFree) -> {
            if (isFree) freeSeats.add(seat);
        });
        Collections.sort(freeSeats);
        return new Cinema(rows, columns, freeSeats);
    }

    Ticket purchaseSeat(Seat requestSeat) {
        for (Map.Entry<Seat, Boolean> entry : seats.entrySet()) {
            Seat seat = entry.getKey();
            Boolean isFree = entry.getValue();
            if (Objects.equals(seat, requestSeat) && isFree) {
                String token = UUID.randomUUID().toString();
                tokens.put(token, seat);
                seats.put(seat, false);
                return new Ticket(token, seat);
            }
        }
        return null;
    }

    Seat returnSeat(String token) {
        Seat seat = tokens.get(token);
        if (!Objects.isNull(seat)) {
            seats.put(seat, true);
            tokens.remove(token);
        }
        return seat;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
