package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Ticket implements Serializable {
    private Integer seatRow;
    private Integer seatCol;
    private Discount discount;

    public Ticket(Seat seat) {
        seatCol = seat.getCol();
        seatRow = seat.getRow();
    }
}
