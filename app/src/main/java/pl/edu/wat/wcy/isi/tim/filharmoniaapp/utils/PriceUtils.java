package pl.edu.wat.wcy.isi.tim.filharmoniaapp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Ticket;

public class PriceUtils {
    public static BigDecimal updatePrice (List<Ticket> tickets, BigDecimal ticketPrice) {
        BigDecimal sum = new BigDecimal(0);
        for (Ticket t: tickets) {
            if (t.getDiscount() != null) {
                BigDecimal discount = new BigDecimal((100 - t.getDiscount().getPercents()) / 100.0);
                sum = sum.add(ticketPrice.multiply(discount));
            }
        }
        return sum.setScale(2, RoundingMode.HALF_UP);
    }
}
