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

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.activities.RepertoireActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos.ConcertDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.Concert;

public class RepertoireController {

    private static String url = RequestSingleton.address + "/concert/approved";
    private RepertoireActivity activity;

    public RepertoireController(RepertoireActivity activity) {
        this.activity = activity;
    }

    public void getConcerts() {
        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null, this::onSuccess, activity::onError));
    }

    public void onSuccess(JSONArray response) {
        ArrayList<Concert> concerts = new ArrayList<>();
        Mapper mapper = Mappers.getMapper(Mapper.class);
        Type listType = new TypeToken<List<ConcertDto>>(){}.getType();
        List <ConcertDto> concertDtos = new Gson().fromJson(response.toString(), listType);
        for (ConcertDto cd: concertDtos) concerts.add(mapper.dtoToConcert(cd));
        activity.listConcerts(concerts);
    }
}
