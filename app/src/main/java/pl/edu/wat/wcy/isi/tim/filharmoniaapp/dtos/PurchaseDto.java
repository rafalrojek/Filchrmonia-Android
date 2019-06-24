package pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseDto  implements Serializable {
    private Integer concertId;
    private String userId;
    private List<TicketDto> tickets;
}
