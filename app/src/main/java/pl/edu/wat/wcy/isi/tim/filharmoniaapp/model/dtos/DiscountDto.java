package pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class DiscountDto  implements Serializable {
    private int idDiscount;
    private String name;
    private int percents;
}
