package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.AndroidSeat;

@Data
public class AndroidSeatDto implements Serializable {
    private int concertId;
    private String androidId;
    private List<SeatDto> seat;
    private AndroidSeat.MessageType type;
}
