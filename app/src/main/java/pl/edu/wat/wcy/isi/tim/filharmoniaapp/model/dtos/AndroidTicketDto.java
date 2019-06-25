package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class AndroidTicketDto implements Serializable {
    private String concertTitle;
    private String date;
    private Double cost;
    private String discount;
    private String concertRoom;
    private int row;
    private int position;

}
