package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeatDto implements Serializable {
    private int row;
    private int col;
}
