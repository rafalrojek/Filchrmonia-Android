package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AndroidSeat implements Serializable {
    private int concertId;
    private String androidId;
    private List<Seat> seat;
    private MessageType type;

    public enum MessageType{
        DISCONNECT,
        FORWARDED,
        LOCKED,
        UNLOCKED
    }
}
