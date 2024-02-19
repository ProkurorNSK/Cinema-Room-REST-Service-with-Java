package cinema;

@SuppressWarnings("unused")
class Seat implements Comparable<Seat>{
    private int row, column, price;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        price = row < 5 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat seat)) return false;

        if (row != seat.row) return false;
        if (column != seat.column) return false;
        return price == seat.price;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + price;
        return result;
    }

    @Override
    public int compareTo(Seat o) {
        if (row > o.getRow()) {
            return 1;
        } else if (row == o.getRow()) {
            if (column > o.getColumn()) {
                return 1;
            } else if (column == o.getColumn()) {
                return 0;
            }else {
                return -1;
            }
        }else {
            return -1;
        }
    }
}
