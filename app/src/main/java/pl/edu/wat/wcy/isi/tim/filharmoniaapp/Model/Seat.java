package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Seat implements Serializable {
    private int row;
    private int col;

    public Seat(int row, int position) {
        this.row = row;
        this.col = position;
    }
}
