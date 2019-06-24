package pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class TicketDto implements Serializable {
    private Integer seatRow;
    private Integer seatCol;
    private String discountName;
}
