package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Purchase  implements Serializable {
    private Integer concertId;
    private String userId;
    private List<Ticket> tickets;

    public Purchase(Integer concertId, String userId, List<Ticket> tickets) {
        this.concertId = concertId;
        this.userId = userId;
        this.tickets = tickets;
    }
}
