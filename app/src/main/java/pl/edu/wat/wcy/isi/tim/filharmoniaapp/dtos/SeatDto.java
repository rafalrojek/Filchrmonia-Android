package pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class SeatDto implements Serializable {
    private int row;
    private int col;
}
