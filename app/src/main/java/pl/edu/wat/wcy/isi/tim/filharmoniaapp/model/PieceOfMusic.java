package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class PieceOfMusic  implements Serializable {
    private int idPiece;
    private String title;
    private String composer;
}
