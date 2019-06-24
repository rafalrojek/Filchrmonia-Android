package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Discount  implements Serializable {
    private int idDiscount;
    private String name;
    private int percents;

    @Override
    public String toString() {
        return name +" (" + percents + "%)";
    }
}
