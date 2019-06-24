package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seat implements Serializable {
    private int row;
    private int col;

    public Seat(int row, int position) {
        this.row = row;
        this.col = position;
    }
}
