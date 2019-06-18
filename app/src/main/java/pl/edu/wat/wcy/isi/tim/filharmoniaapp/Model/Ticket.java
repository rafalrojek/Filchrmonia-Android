package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Ticket implements Serializable {
    private Integer seatRow;
    private Integer seatCol;
    private String discountName;

    public Ticket(Seat seat) {
        seatCol = seat.getCol();
        seatRow = seat.getRow();
    }
}
