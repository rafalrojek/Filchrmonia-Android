package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class Concert implements Serializable {
    private int idConcert;
    private String concertTitle;
    private String date;
    private BigDecimal additionalOrganisationCosts;
    private boolean isApproved;
    private BigDecimal ticketCost;
    private String concertRoomName;
    private String concertRoomAddress;
    private String concertPerformers;
    private List<PieceOfMusic> repertoire;
}
