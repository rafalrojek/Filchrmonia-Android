package pl.edu.wat.wcy.isi.tim.filharmoniaapp.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

import lombok.Data;

@Entity(tableName = "AndroidTicket")
@Data
public class AndroidTicket implements Serializable {
    @PrimaryKey private int id;
    private String AndroidID;
    private String name;
    private String date;
    private Double cost;
    private String discount;
    private String room;
    private int row;
    private int position;

}
