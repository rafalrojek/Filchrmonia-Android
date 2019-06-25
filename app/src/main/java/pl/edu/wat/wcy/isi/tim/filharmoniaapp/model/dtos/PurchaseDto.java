package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PurchaseDto  implements Serializable {
    private Integer concertId;
    private String userId;
    private List<TicketDto> tickets;
}
