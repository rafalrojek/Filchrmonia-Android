package pl.edu.wat.wcy.isi.tim.filharmoniaapp.controller;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import pl.edu.wat.wcy.isi.tim.filharmoniaapp.R;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.view.activities.SelectSeatActivity;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.AndroidSeatDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.Mapper;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.dtos.SeatDto;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.AndroidSeat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Seat;
import pl.edu.wat.wcy.isi.tim.filharmoniaapp.model.model.Ticket;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompCommand;
import ua.naiksoftware.stomp.dto.StompHeader;
import ua.naiksoftware.stomp.dto.StompMessage;

public class SelectSeatController {

    private SelectSeatActivity activity;
    private StompClient stompClient;
    private UUID uuid;
    private int concertId;
    private ArrayList<Ticket> tickets;
    private Mapper mapper = Mappers.getMapper(Mapper.class);

    @SuppressLint("CheckResult")
    public SelectSeatController(SelectSeatActivity activity, int concertId) {
        this.activity = activity;
        this.concertId = concertId;
        this.uuid = UUID.randomUUID();
        this.tickets = new ArrayList<>();

        String ws = RequestSingleton.getProperty("WS",activity.getApplicationContext());
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP,ws);
        stompClient.connect();
        StompUtils.lifecycle(stompClient);

        AndroidSeat androidSeat = new AndroidSeat(concertId, uuid.toString(), null, null);
        AndroidSeatDto androidSeatDto = mapper.androidSeatToDto(androidSeat);

        stompClient.send(new StompMessage(StompCommand.SEND,
                Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.addUser")),
                new Gson().toJson(androidSeatDto))).subscribe();

        stompClient.topic("/concert/seats/"+concertId)
                .subscribe(stompMessage ->  onMessage(stompMessage.getPayload()));
    }


    private void onMessage(String text) {
        AndroidSeatDto androidSeatDto = new Gson().fromJson(text, AndroidSeatDto.class);
        AndroidSeat androidSeat = mapper.dtoToAndroidSeat(androidSeatDto);
        if(androidSeat.getConcertId() != concertId) return;

        for (Seat seat: androidSeat.getSeat()) {

            if (androidSeat.getType() == AndroidSeat.MessageType.LOCKED) {
                if (uuid.toString().equals(androidSeat.getAndroidId())) {
                    tickets.add(new Ticket(seat));
                    activity.switchCheckbox(seat, true, true);
                }
                else removeTicket(seat, false);
            }
            else removeTicket(seat, true);
        }
    }

    private void removeTicket(Seat seat, boolean enabled) {
        activity.switchCheckbox(seat, enabled, false);
        for (Ticket t: tickets)
            if (t.getSeatCol() == seat.getCol() && t.getSeatRow() == seat.getRow()) {
                tickets.remove(t);
                return;
            }
    }

    public void SendSeatMessage(boolean isChecked, Seat seat) {
        ArrayList<Seat> seats = new ArrayList<>();
        seats.add(seat);

        AndroidSeat androidSeat;
        if (isChecked) androidSeat = new AndroidSeat(concertId, uuid.toString(), seats, AndroidSeat.MessageType.LOCKED);
        else androidSeat = new AndroidSeat(concertId, uuid.toString(), seats, AndroidSeat.MessageType.UNLOCKED);

        AndroidSeatDto androidSeatDto = mapper.androidSeatToDto(androidSeat);

        stompClient.send(new StompMessage(StompCommand.SEND,
                Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.sendMessage")),
                new Gson().toJson(androidSeatDto))).subscribe();
    }

    public ArrayList<Ticket> forwardMessage() {
        AndroidSeat androidSeat = new AndroidSeat(concertId, uuid.toString(), null, AndroidSeat.MessageType.FORWARDED);
        AndroidSeatDto androidSeatDto = mapper.androidSeatToDto(androidSeat);

        stompClient.send(new StompMessage(StompCommand.SEND,
                Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.sendMessage")),
                new Gson().toJson(androidSeatDto))).subscribe();

        stompClient.disconnect();
        return tickets;
    }

    public void disconnectMessage() {
        AndroidSeat androidSeat = new AndroidSeat(concertId, uuid.toString(), null, AndroidSeat.MessageType.DISCONNECT);
        AndroidSeatDto androidSeatDto = mapper.androidSeatToDto(androidSeat);
        stompClient.send(new StompMessage(StompCommand.SEND,
                Collections.singletonList(new StompHeader(StompHeader.DESTINATION, "/app/socket.sendMessage")),
                new Gson().toJson(androidSeatDto))).subscribe();
    }

    public void getSeats(int idConcert) {
        String address = RequestSingleton.getProperty("REST",activity.getApplicationContext());
        String url = address + "/free-seat/"+idConcert;
        RequestSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(new JsonArrayRequest
                (Request.Method.GET, url, null,
                        this::OnResponse,
                        this::onErrorResponse)
        );
    }

    private void OnResponse(JSONArray response) {
        Type listType = new TypeToken<List<SeatDto>>(){}.getType();
        ArrayList<SeatDto> seatsDto = new Gson().fromJson(response.toString(), listType);
        for (SeatDto sd: seatsDto) {
            Seat s = mapper.dtoToSeat(sd);
            activity.switchCheckbox(s,true,false);
        }
    }

    private void onErrorResponse(VolleyError error) {
        Toast.makeText(activity.getApplicationContext(), R.string.noTicketError, Toast.LENGTH_SHORT).show();
        activity.finish();
    }

    public boolean isEmpty() {return tickets.isEmpty();}
}
