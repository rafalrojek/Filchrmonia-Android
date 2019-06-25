package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Entity(tableName = "AndroidTicket")
@Data
@ToString
public class AndroidTicket implements Serializable {
    @PrimaryKey private int id;
    private String concertTitle;
    private String date;
    private Double cost;
    private String discount;
    private String concertRoom;
    private int row;
    private int position;

}
