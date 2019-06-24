package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controllers;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities.DiscountActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos.DiscountDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Discount;

public class DiscountController {

    private DiscountActivity activity;
    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public DiscountController(DiscountActivity discountActivity) {
        this.activity = discountActivity;
    }

    public void getDiscounts() {
        String url = RequestSingleton.address + "/discount";
        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null,
                        this::onResponse,
                        activity::onErrorResponse)
        );
    }

    private void onResponse(JSONArray jsonArray) {
        Type listType = new TypeToken<List<DiscountDto>>(){}.getType();
        ArrayList<DiscountDto> discountDtos = new Gson().fromJson(jsonArray.toString(), listType);
        ArrayList<Discount> discounts = new ArrayList<>();
        for (DiscountDto dd: discountDtos) discounts.add(mapper.dtoToDiscount(dd));
        activity.configAdapter(discounts);
    }

}
