package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities.RepertoireActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.ConcertDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Concert;

public class RepertoireController {

    private RepertoireActivity activity;

    public RepertoireController(RepertoireActivity activity) {
        this.activity = activity;
    }

    public void getConcerts() {
        String address = RequestSingleton.getProperty("REST",activity.getApplicationContext());
        String url = address + "/concert/approved";
        RequestSingleton.getInstance(activity.getApplicationContext());
        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null, this::onSuccess, activity::onError));
    }

    private void onSuccess(JSONArray response) {
        ArrayList<Concert> concerts = new ArrayList<>();
        Mapper mapper = Mappers.getMapper(Mapper.class);
        Type listType = new TypeToken<List<ConcertDto>>(){}.getType();
        List <ConcertDto> concertDtos = new Gson().fromJson(response.toString(), listType);
        for (ConcertDto cd: concertDtos) concerts.add(mapper.dtoToConcert(cd));
        activity.listConcerts(concerts);
    }
}
