package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import android.view.View;
import android.widget.AdapterView;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.mapstruct.factory.Mappers;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Ticket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.utils.PriceUtils;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities.DiscountActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.DiscountDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Discount;

public class DiscountController {

    private DiscountActivity activity;
    private Mapper mapper = Mappers.getMapper(Mapper.class);
    private ArrayList<Discount> discounts;
    List<Ticket> tickets;
    BigDecimal ticketPrice;

    public DiscountController(DiscountActivity discountActivity,
                              List<Ticket> tickets, BigDecimal ticketPrice) {
        this.activity = discountActivity;
        this.ticketPrice = ticketPrice;
        this.tickets = tickets;
    }

    public void getDiscounts() {
        String address = RequestSingleton.getProperty("REST",activity.getApplicationContext());
        String url = address + "/discount";
        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null,
                        this::onResponse,
                        activity::onErrorResponse)
        );
    }

    private void onResponse(JSONArray jsonArray) {
        Type listType = new TypeToken<List<DiscountDto>>(){}.getType();
        ArrayList<DiscountDto> discountDtos = new Gson().fromJson(jsonArray.toString(), listType);
        discounts = new ArrayList<>();
        for (DiscountDto dd: discountDtos) discounts.add(mapper.dtoToDiscount(dd));
        activity.configAdapter(discounts);
    }

    public void onItemSelected(Ticket ticket, int i) {
        ticket.setDiscount(discounts.get(i));
        BigDecimal sum  = PriceUtils.updatePrice(tickets, ticketPrice);
        activity.setPrice(sum.toPlainString());
    }

    public void onNothingSelected(Ticket ticket) {
        ticket.setDiscount(null);
    }
}