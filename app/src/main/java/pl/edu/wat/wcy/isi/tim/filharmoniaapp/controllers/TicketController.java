package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controllers;

import android.annotation.SuppressLint;
import android.provider.Settings;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities.TicketsActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.database.DatabaseSingleton;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos.AndroidTicketDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.AndroidTicket;

public class TicketController {

    private TicketsActivity activity;
    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public TicketController(TicketsActivity activity) {
        this.activity = activity;
    }

    public List<AndroidTicket> getTickets() {return DatabaseSingleton.get(activity.getApplicationContext()).getTickets();}

    public void downloadTickets(View view) {

        @SuppressLint("HardwareIds")
        String android_id = Settings.Secure.getString(activity.getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String url = RequestSingleton.address + "/ticket?id=" + android_id;

        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest(
                Request.Method.POST, url, null, this::onResponse, activity::onErrorResponse));
    }

    private void onResponse(JSONArray response) {
        Type listType = new TypeToken<List<AndroidTicketDto>>(){}.getType();
        DatabaseSingleton db = DatabaseSingleton.get(activity.getApplicationContext());
        db.deleteAll();
        ArrayList<AndroidTicketDto> ticketDtos = new ArrayList<>(new Gson().fromJson(response.toString(), listType));
        for (AndroidTicketDto atd: ticketDtos) {
            AndroidTicket at = mapper.dtoToAndroidTicket(atd);
            at.setId(db.getIndex());
            db.insertTicket(at);
        }
        activity.updateTickets(db.getTickets());
    }
}
