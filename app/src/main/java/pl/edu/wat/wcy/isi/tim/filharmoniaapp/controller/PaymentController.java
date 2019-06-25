package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import android.content.Intent;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapstruct.factory.Mappers;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities.PaymentActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities.TicketsActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.database.DatabaseSingleton;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.AndroidTicketDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.PurchaseDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.entities.AndroidTicket;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Purchase;

public class PaymentController {

    private PaymentActivity activity;
    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public PaymentController(PaymentActivity activity) {
        this.activity = activity;
    }

    public void sendPayment(String url) {
        String address = RequestSingleton.getProperty("REST",activity.getApplicationContext());
        String send = address + "/paypal/payment/complete" + url.substring(38);
        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest(
                Request.Method.POST, send, null, this::onPaidResponse, activity::onErrorResponse));
    }

    public void makePayment(Purchase purchase) {
        PurchaseDto purchaseDto = mapper.purchaseToDto(purchase);
        String address = RequestSingleton.getProperty("REST",activity.getApplicationContext());
        String url = address + "/paypal/payment/make";
        JSONObject object = new JSONObject();

        try {
            object = new JSONObject(new Gson().toJson(purchaseDto));
        } catch (JSONException e) {
            Toast.makeText(activity.getApplicationContext(), R.string.aksError, Toast.LENGTH_SHORT).show();
        }

        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonObjectRequest(
                Request.Method.POST, url, object, this::onPayPalUrl, activity::onErrorResponse));
    }

    private void onPayPalUrl(JSONObject jsonObject) {
        try {
            String url = jsonObject.getString("url");
            activity.onResponse(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onPaidResponse(JSONArray response) {
        Type listType = new TypeToken<List<AndroidTicketDto>>(){}.getType();
        DatabaseSingleton db = DatabaseSingleton.get(activity.getApplicationContext());
        ArrayList<AndroidTicketDto> ticketsDto = new ArrayList<>(new Gson().fromJson(response.toString(), listType));
        for (AndroidTicketDto atd : ticketsDto) {
            AndroidTicket at = mapper.dtoToAndroidTicket(atd);
            at.setId(db.getIndex());
            db.insertTicket(at);
        }
        Intent i = new Intent(activity, TicketsActivity.class);
        activity.startActivity(i);
    }
}
