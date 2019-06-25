package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class PieceOfMusicDto  implements Serializable {
    private int idPiece;
    private String title;
    private String composer;
}
