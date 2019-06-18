package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class Purchase  implements Serializable {
    private Integer concertId;
    private String AndroidID;
    private List<Ticket> tickets;

    public Purchase(Integer concertId, String androidID, List<Ticket> tickets) {
        this.concertId = concertId;
        AndroidID = androidID;
        this.tickets = tickets;
    }
}
